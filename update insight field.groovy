import java.util.Objects
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.bc.user.search.UserSearchParams
import com.atlassian.jira.bc.JiraServiceContextImpl
import com.atlassian.jira.bc.JiraServiceContext
import com.atlassian.jira.jql.parser.JqlQueryParser
import org.apache.xml.security.c14n.implementations.Canonicalizer11_OmitComments
import com.atlassian.jira.bc.filter.SearchRequestService
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.query.Query
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
@WithPlugin("com.riadalabs.jira.plugins.insight")
import com.riadalabs.jira.plugins.insight.channel.external.api.facade.ObjectFacade
import com.riadalabs.jira.plugins.insight.services.model.ObjectAttributeBean
import com.riadalabs.jira.plugins.insight.services.model.ObjectBean

import org.apache.log4j.Level
log.setLevel(Level.ALL)
log.setAdditivity(false)

// активный пользователь (админ)
def curuser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

// используем класс objectfacade 
def objectFacade = ComponentAccessor.getOSGiComponentInstanceOfType(ObjectFacade)

// задаем значения для каждого объекта, которые будут обновлять insight поле
def apbean = objectFacade.loadObjectBean(8108)
def drbean = objectFacade.loadObjectBean(8109)
def fkbean = objectFacade.loadObjectBean(8110)
def svbean = objectFacade.loadObjectBean(8111)
def mbbean = objectFacade.loadObjectBean(8112)

// используем объект задачи как mutableissue
MutableIssue issue = ComponentAccessor.issueManager.getIssueObject("AML-112")
// используем объект инсайт поля
def brandinsight = ComponentAccessor.customFieldManager.getCustomFieldObject("customfield_13124")
// используем объект старого поля (поле - чекбоксы)
def brandold = ComponentAccessor.customFieldManager.getCustomFieldObject("customfield_11726")


// проверяем какие объекты есть в старом поле
issue.getCustomFieldValue(brandold).each { brand ->
  
  // пустой лист, в который попадут объекты для обновления
  def dataforupdate = []
  
  // несколько проверок на объекты и добавление нужных инсайт объектов в поле для обновления
    if(brand.getValue() == "sv"){
        dataforupdate.add(svbean)
    }
    if(brand.getValue() == "dr"){
        dataforupdate.add(drbean)
    }
    if(brand.getValue() == "mb"){
        dataforupdate.add(mbbean)
    }
    if(brand.getValue() == "fk"){
        dataforupdate.add(fkbean)
    }
    if(brand.getValue() == "ap"){
        dataforupdate.add(apbean)
    }
  // проверка
  log.info(dataforupdate)
 }


// обновляем инсайт поле
issue.setCustomFieldValue(brandinsight, dataforupdate)
ComponentAccessor.issueManager.updateIssue(curuser, issue, EventDispatchOption.DO_NOT_DISPATCH, false)
