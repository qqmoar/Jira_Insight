import com.atlassian.jira.component.ComponentAccessor

Class objectFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade")
Class objectAttributeBeanValueClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.services.model.ObjectAttributeValueBean")
def objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(objectFacadeClass)
def objectAttributeBeanValue = ComponentAccessor.getOSGiComponentInstanceOfType(objectAttributeBeanValueClass)

String objectKey = "TEST-719"
def insightObject = objectFacade.loadObjectBean(objectKey)


attribute = objectFacade.loadObjectAttributeBean(719, 850)

attribute.getObjectAttributeValueBeans()[0].getValue()
