/*
здесь нужно учесть следующее:
В инсайт уже слиты все объекты 1го уровня, они в справочнике 117
в инсайт слиты все объекты 2,3,4,5-12 уровней, они в другом справочнике 143

шаблон insightUpdateDraft для rest API работает по справочнику 1го уровня из интры
в конце делей в 200мс, иначе никак
*/


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.atlassian.jira.component.ComponentAccessor

//	объект класса
def jsonSlurper = new JsonSlurper()
def jsonOutput = new JsonOutput()

// пустышка
def res = ""

//	подключаем классы инсайта
Class iqlFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.IQLFacade")
//	объект класса
iqlFacade = ComponentAccessor.getOSGiComponentInstanceOfType(iqlFacadeClass)

//	юрла и хедеры в инсайт, чтобы обновлять аттрибуты внутри
def insightURL = "https://YOURJIRAADDRESS/rest/insight/1.0/object/"
Map<String, String> headers = new HashMap<String, String>()
headers["Content-Type"] = "application/json"
headers["Authorization"] = "Bearer YOURKEYHERE"

def unitKeys = [4, 23, 1225, 1789, 1661, 687, 52, 1399, 53, 777, 2359, 54, 124, 663, 5423, 3851, 13659, 2949, 14901, 16422, 17171, 1733, 15769, 5669, 13675, 
  17608, 14063, 1068, 1327, 14003, 6807, 16506, 15567, 8557, 6273, 13483, 13383, 13473, 7945, 15109, 8803, 15561, 16507, 13719, 7185, 3899, 
  5733, 1787, 13577, 14277]


for (unit in unitKeys){
  // пустышка
  def insightChildrenObjects
//	драфт json которым обновляем данные
//	справочник 117, аттрибут 1215 у объектов, [] - сюда будем значения подпихивать по ключам
def insightUpdateDraft = """
{
    "objectTypeId": 117,
    "attributes": [
        {
            "objectTypeAttributeId": 1215,
            "objectAttributeValues": []
        }
    ]
}
"""

//	собираем из драфта json
def jsonDraft = jsonSlurper.parseText(insightUpdateDraft)
//	обращаемся к массиву с объектами аттрибутов
def attValues = jsonDraft.attributes[0].objectAttributeValues
//	запрос в интранет
  def intraURL = "https://home.vk.team/api/v2/units/${unit}/?descendants=1&api_key=API_KEY_JIRA&api_app=jira"
def req = _.GET(intraURL).objects
//	получаем название хед юнита
def headUnit = req.find{it.level == 1}.name

//	находим объект в инсайте для хед юнита---- получаем его айдишку, ее мы будем подставлять в insightURL 
def insightHeadObjectId = iqlFacade.findObjects("objectTypeId = 117 and Name = \"${headUnit}\"")?.first().id


//	получаем дочерки 2 уровня
def childNames = req.findAll{it.parent_id == unit}*.name

//	находим объекты в инсайте дочерки
  if(childNames){
  insightChildrenObjects = iqlFacade.findObjects("objectTypeId = 143 and Name in (${childNames.collect { "\"${it.replace('"', "'")}\"" }.join(", ")})")*.id
  }else{
    continue
  }
//	добавляем объекты в наш драфт json
insightChildrenObjects.each{ attValues.add("value":it)}


//	формируем json
def jsonOut = jsonOutput.toJson(jsonDraft)

//	обновляем данные
_.PUT(insightURL + insightHeadObjectId, jsonOut, "LOGIN", "PASSWORD")
  Thread.sleep(200)

}
