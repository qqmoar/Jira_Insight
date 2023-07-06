import com.atlassian.jira.component.ComponentAccessor

Class objectFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade")

objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(objectFacadeClass)


String objectKey = "TEST-733"
def insightObject = objectFacade.loadObjectBean(objectKey)

//attribute = objectFacade.loadObjectAttributeBean(insightObject.id, 850).getObjectAttributeValueBeans()[0].getValue()

getDeveloper(insightObject)

def getDeveloper(insightObject) {
  def developerKey = objectFacade.loadObjectAttributeBean(insightObject.id, 850).getObjectAttributeValueBeans()?.first()?.getValue()
  return developerKey
}

def getAnalytic(insightObject) {
  def analyticKey = objectFacade.loadObjectAttributeBean(insightObject.id, 851).getObjectAttributeValueBeans()?.first()?.getValue()
  return analyticKey
}



//  есть проблема, что некоторых юзеров получаешь с их usernamе, а какие-то через JIRAUSER9999
