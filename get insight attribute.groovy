import com.atlassian.jira.component.ComponentAccessor

//	подключаем классы инсайта
Class objectFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade")

//	объект класса
objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(objectFacadeClass)

//	ключ объекта
String objectKey = "TEST-719"
//	получаем объект в виде инсайт корзины
def insightObject = objectFacade.loadObjectBean(objectKey)

getAttributeValue(insightObject, 850)

//
//	функции
//
def getAttributeValue(insightObject, int attributeId) {
  //  получаем значение аттрибута через айди объекта и айди аттрибута
  def attributeVal = objectFacade.loadObjectAttributeBean(insightObject.id, attributeId).getObjectAttributeValueBeans()?.first()?.getValue()
  //  возвращает String
  return attributeVal
}



//  есть проблема при аттрибуте с пользаками, потмоу что некоторых юзеров получаешь с их usernamе, а какие-то в виде JIRAUSER9999
