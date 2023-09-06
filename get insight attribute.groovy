import com.atlassian.jira.component.ComponentAccessor

import com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade
@WithPlugin("com.riadalabs.jira.plugins.insight")
@PluginModule
ObjectFacade objectFacade


//	ключ объекта
String objectKey = "TEST-719"
//	получаем объект в виде инсайт корзины
def insightObject = objectFacade.loadObjectBean(objectKey)

getAttributeValue(insightObject, 850)

//
//	функции
//
//  получаем знач-е аттр-та объекта по айди аттрибута
def getAttributeValue(insightObject, int attributeId) {
  if(insightObject){
    //  получаем значение аттрибута через айди объекта и айди аттрибута (получает первый объект в списке)
    def attributeVal = objectFacade.loadObjectAttributeBean(insightObject?.id, attributeId).getObjectAttributeValueBeans()?.first()?.getValue()
    //  получает все объекты из поля аттрибута
    //  def attributeVal = objectFacade.loadObjectAttributeBean(insightObject.id, attributeId).getObjectAttributeValueBeans()?.collect{it.getValue()}

    //  возвращает String, если один элемент или array, если много
    return attributeVal
  }else{
    return null
  }
}



//  есть проблема при аттрибуте с пользаками, потмоу что некоторых юзеров получаешь с их usernamе, а какие-то в виде JIRAUSER9999







/*  ---- старое подключение класса
//	подключаем классы инсайта
Class objectFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade")

//	объект класса
objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(objectFacadeClass)
*/
