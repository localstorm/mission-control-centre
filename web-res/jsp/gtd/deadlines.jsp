<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>TASK</span> deadlines</h2>
 <table width="100%">
    <tr>
        <td align="left">
            <jsp:include page="/WEB-INF/jsp/includes/gtd/ctxFilter.jsp">
                <jsp:param name="returnPageToken" value="${returnPageToken}" />
            </jsp:include>
        </td>
    </tr>
</table>
<br/>
<table width="100%">
<c:forEach items="${actionBean.broken}" var="taskMarker" >

<tr bgcolor="<c:choose><c:when test="${taskMarker.redCrossed}">#FFCF91</c:when><c:otherwise>#A3A69C</c:otherwise></c:choose>">
    <td>
        <p><img src="<c:url value="/images/loe${taskMarker.task.effort}.png"/>"/>&nbsp;(<c:out value="${taskMarker.timeRemainsCrossed}"/>)&nbsp;<span><c:out value="${taskMarker.task.list.context.name}, ${taskMarker.task.list.name}"/>&nbsp;(Since: <fmt:formatDate value="${taskMarker.task.creation}"/>):</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${taskMarker.task.id}" />
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                    </c:url>"><c:out value="${taskMarker.task.summary}" /></a>
        </div>
        <c:if test="${not empty taskMarker.task.details}" >
            &nbsp;<a href="#" onclick="$('#details_b_<c:out value="${taskMarker.task.id}"/>').animate({height:'toggle',opacity:'toggle'}, 'fast'); return false">Details (click to view/hide)</a>
            <div align="justify" id="details_b_<c:out value="${taskMarker.task.id}"/>" style="display: none;" >
                <c:out escapeXml="false" value="${taskMarker.task.detailsHtmlEscaped}"/>
            </div>
        </c:if>
        </p>
        
        <c:if test="${not empty taskMarker.task.runtimeNote}" >
            <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${taskMarker.task.runtimeNote}"/></p>
        </c:if>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="1%" >
                <nobr>
                    <c:if test="${not taskMarker.task.inFlightPlan}">
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" src="<c:url value="/images/flight.png"/>"/></a>
                    </c:if>
                    <a href="<c:url value="/actions/gtd/ctx/list/ViewList" >
                        <c:param name="listId" value="${taskMarker.task.list.id}" />
                    </c:url>" title="Open affected list"><img alt="toList" src="<c:url value="/images/toList.png"/>"/></a>
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="action" value="FINISH" />
                             </c:url>" title="Finish"><img alt="finish" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="action" value="CANCEL" />
                             </c:url>" title="Cancel"><img alt="cancel" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>

        </table>
    </td>
</tr>
</c:forEach>
<tr>
    <td align="center">
        <table width="100%">
            <tr>
                <td width="80%"><hr style="height: 3px; color: #f00; background-color: #f00;"/></td>
                <td><fmt:formatDate value="${actionBean.today}"/></td>
            </tr>
        </table>
    </td>
</tr>
<c:forEach items="${actionBean.following}" var="taskMarker" >

    <tr bgcolor="<c:choose><c:when test="${taskMarker.redNonCrossed}">#FFCF91</c:when><c:otherwise>#A3A69C</c:otherwise></c:choose>">
    <td>
        <p><img src="<c:url value="/images/loe${taskMarker.task.effort}.png"/>"/>&nbsp;(<c:out value="${taskMarker.timeRemainsNonCrossed}"/>)&nbsp;<span><c:out value="${taskMarker.task.list.context.name}, ${taskMarker.task.list.name}"/>&nbsp;(Since: <fmt:formatDate value="${taskMarker.task.creation}"/>):</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${taskMarker.task.id}" />
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                    </c:url>"><c:out value="${taskMarker.task.summary}" /></a>
        </div>
        <c:if test="${not empty taskMarker.task.details}" >
            &nbsp;<a href="#" onclick="$('#details_f_<c:out value="${taskMarker.task.id}"/>').animate({height:'toggle',opacity:'toggle'}, 'fast'); return false">Details (click to view/hide)</a>
            <div align="justify" id="details_f_<c:out value="${taskMarker.task.id}"/>" style="display: none;" >
                <c:out escapeXml="false" value="${taskMarker.task.detailsHtmlEscaped}"/>
            </div>
        </c:if>
        </p>

        <c:if test="${not empty taskMarker.task.runtimeNote}" >
            <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${taskMarker.task.runtimeNote}"/></p>
        </c:if>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="1%" >
                <nobr>
                    <c:if test="${not taskMarker.task.inFlightPlan}">
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" src="<c:url value="/images/flight.png"/>"/></a>
                    </c:if>
                    <a href="<c:url value="/actions/gtd/ctx/list/ViewList" >
                        <c:param name="listId" value="${taskMarker.task.list.id}" />
                    </c:url>" title="Open affected list"><img alt="toList" src="<c:url value="/images/toList.png"/>"/></a>
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="action" value="FINISH" />
                             </c:url>" title="Finish"><img alt="finish" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${taskMarker.task.id}" />
                                <c:param name="action" value="CANCEL" />
                             </c:url>" title="Cancel"><img alt="cancel" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>

        </table>
    </td>
</tr>
</c:forEach>
</table>

<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>