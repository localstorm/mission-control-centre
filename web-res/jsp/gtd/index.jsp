<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>FLIGHT</span> plan</h2>
    <table width="100%">
        <tr>
            <td align="left">
                <jsp:include page="/WEB-INF/jsp/includes/gtd/ctxFilter.jsp">
                    <jsp:param name="returnPageToken" value="${returnPageToken}" />
                </jsp:include>
            </td>
                
            <td align="right">
                <c:if test="${(not empty actionBean.flightPlanTasks) or (not empty actionBean.awaitedFlightPlanTasks) or (not empty actionBean.archiveFlightPlanTasks)}">
                    <a onclick="return confirm('Are you sure?');" href="<c:url value="/actions/gtd/nil/UtilizeFlightPlan" />" title="Utilize &amp; build new"><img src="<c:url value="/images/unhint.png"/>" /></a>
                </c:if>
            </td>
        </tr>
    </table>
<br/>
<c:if test="${not empty actionBean.flightPlanTasks}">
<table width="100%"><tr><th>Operative</th></tr></table> 
<c:forEach items="${actionBean.flightPlanTasks}" var="task">
    <p><span><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<c:out value="${task.list.context.name}, ${task.list.name}" />:</span><br/>
    <div align="center">
        <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                        <c:param name="taskId" value="${task.id}" />
                        <c:param name="returnPageToken" value="${returnPageToken}" />
                </c:url>" title="Expand"><c:out value="${task.summary}" /></a>
    </div>
    </p>
<div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
    <stripes:form action="/actions/gtd/ctx/list/task/ResolveTask" >
        <stripes:hidden name="returnPageToken" value="${returnPageToken}" />
        <stripes:hidden name="taskId" value="${task.id}" />
        <stripes:hidden name="action" value="DELEGATE" />
        <table width="100%" style="background:#FFFFD0; border:1px dotted #DADADA;">
            <tr>
                <td width="80%" align="center"><stripes:text name="runtimeNote" id="rtn-${task.id}" style="width: 95%"/></td>
                <td width="10%" align="center"><stripes:submit name="s1" value="Delegate" style="width: 7em;" /></td>
                <td width="10%" align="center"><stripes:submit name="s1" value="Cancel" style="width: 7em;" onclick="hide('delegate-${task.id}'); return false" /></td>
            </tr>
        </table>
    </stripes:form>
</div>
<table width="100%">
    <tr>
        <td width="80%" ><hr/></td>
        <td width="20%" >
        <nobr>
            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNFLIGHT" />
            </c:url>" title="Remove from flight plan"><img alt="unflight" src="<c:url value="/images/unflight.png"/>"/></a>
            <a href="<c:url value="/actions/gtd/ctx/list/ViewList" >
                <c:param name="listId" value="${task.list.id}" />
            </c:url>" title="Open affected list"><img alt="toList" src="<c:url value="/images/toList.png"/>"/></a>
            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="FINISH" />
            </c:url>" title="Finish"><img alt="finish" src="<c:url value="/images/finish.png"/>"/></a>
            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="CANCEL" />
            </c:url>" title="Cancel"><img alt="cancel" src="<c:url value="/images/cancel.png"/>"/></a>
            <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" src="<c:url value="/images/delegate.png"/>"/></a>
        </nobr>
        </td>
    </tr>
</table>
</c:forEach>
</c:if>

<c:if test="${not empty actionBean.awaitedFlightPlanTasks}">
<table width="100%"><tr><th>Awaited</th></tr></table> 
<c:forEach items="${actionBean.awaitedFlightPlanTasks}" var="task">

    <p><a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNDELEGATE" />
                        </c:url>" title="Not delegated"><img src="<c:url value="/images/delegated.png"/>" /></a>
                        <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNFLIGHT" />
                        </c:url>" title="Remove from flight plan"><img alt="unflight" src="<c:url value="/images/unflight.png"/>"/></a>
                        <span><c:out value="${task.list.context.name}, ${task.list.name}" />:</span><br/>
                        <div align="center">
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                            </c:url>"><c:out value="${task.summary}"/></a>
                        </div>
                        <c:if test="${not empty task.runtimeNote}">
                            <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
                        </c:if>

                        </p>
<hr/>
</c:forEach>
</c:if>

<c:if test="${not empty actionBean.archiveFlightPlanTasks}">
<table width="100%"><tr><th>Archive</th></tr></table> 
<c:forEach items="${actionBean.archiveFlightPlanTasks}" var="task" >
<p>
    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNRESOLVE" />
            </c:url>" title="Unresolve"><img src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>" /></a>
    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNFLIGHT" />
            </c:url>" title="Remove from flight plan"><img alt="unflight" src="<c:url value="/images/unflight.png"/>"/></a>            
    <span><c:out value="${task.list.context.name}, ${task.list.name}" />:</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                        <c:param name="taskId" value="${task.id}" />
                        <c:param name="returnPageToken" value="${returnPageToken}" />
                </c:url>"><c:out value="${task.summary} "/></a>
        </div>
    <hr/>
</p>
</c:forEach>
</c:if>
	
 
<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>