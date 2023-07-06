import com.atlassian.jira.component.ComponentAccessor

Class objectFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade")

objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(objectFacadeClass)


String objectKey = "TEST-733"
def insightObject = objectFacade.loadObjectBean(objectKey)

//attribute = objectFacade.loadObjectAttributeBean(insightObject.id, 850).getObjectAttributeValueBeans()[0].getValue()

getAttributeValue(insightObjectб 850)

def getAttributeValue(insightObject, int attributeId) {
  //  получаем значение аттрибута через айди объекта и айди аттрибута
  def attributeVal = objectFacade.loadObjectAttributeBean(insightObject.id, attributeId).getObjectAttributeValueBeans()?.first()?.getValue()
  return attributeVal
}


//  есть проблема при аттрибуте с пользаками, потмоу что некоторых юзеров получаешь с их usernamе, а какие-то в виде JIRAUSER9999
