import com.atlassian.jira.component.ComponentAccessor

/*
в этом кейсе ищу объекты их справочника, названия которых = названию компонента в задаче
обновляю поле в задаче объектом
*/


//	подключаем классы инсайта
Class iqlFacadeClass = ComponentAccessor.getPluginAccessor().getClassLoader().findClass("com.riadalabs.jira.plugins.insight.channel.external.api.facade.IQLFacade")

//	объект класса
iqlFacade = ComponentAccessor.getOSGiComponentInstanceOfType(iqlFacadeClass)


//	объект поля
def insightComponentCF = _.getCustomFieldObject(79374)
//	получить компонент
def componentName = issue.components[0].name
//	найти компонент в объектах инсайта
def insightObject = iqlFacade.findObjects("objectTypeId = 140 and Name = \"${componentName}\"")
//	если нашлось - забить значение в задачу
issue.setCustomFieldValue(insightComponentCF, insightObject)
_.update(issue)
