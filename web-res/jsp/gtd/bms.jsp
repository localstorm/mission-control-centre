<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<c:choose>
    <c:when test="${actionBean.filter eq 'HINTED'}">
        <h2><span>HINTED</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'PENDING'}">
        <h2><span>PENDING</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'AWAITED'}">
        <h2><span>AWAITED</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'FLIGHT'}">
        <h2><span>FLIGHT PLAN</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'REDLINE'}">
        <h2><span>BROKEN</span> red line tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'DEADLINE'}">
        <h2><span>BROKEN</span> dead line tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'FIN'}">
        <h2><span>FINISHED</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'EASY'}">
        <h2><span>EASY</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'ELEMENTARY'}">
        <h2><span>ELEMENTARY</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'MEDIUM'}">
        <h2><span>MEDIUM</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'DIFFICULT'}">
        <h2><span>DIFFICULT</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'VERY_DIFFICULT'}">
        <h2><span>VERY DIFFICULT</span> tasks</h2>
    </c:when>
    <c:otherwise>
        <h2><span>????</span> tasks</h2>
    </c:otherwise>
</c:choose>

<br/>
<div align="center">
    <h4><a href="<c:url value="/actions/gtd/ctx/ViewContext">
        <c:param name="contextId" value="${actionBean.contextResult.id}" />
    </c:url>"><c:out value="${actionBean.contextResult.name}"/></a></h4>
</div>

<c:forEach items="${actionBean.tasks}" var="task">
    <p><span><c:choose>
                <c:when test="${(not task.finished) and (not task.cancelled)}">
                    <c:choose>
                        <c:when test="${not task.delegated}">
                            <img src="<c:url value="/images/loe${task.effort}.png"/>"/>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="UNDELEGATE" />
                            </c:url>" title="Not delegated"><img src="<c:url value="/images/delegated.png"/>"/></a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="UNRESOLVE" />
                            </c:url>" title="Unresolve"><img src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>"/></a>
                    <c:if test="${task.inFlightPlan}">
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                        <c:param name="returnPageToken" value="${returnPageToken}" />
                                        <c:param name="taskId" value="${task.id}" />
                                        <c:param name="action" value="UNFLIGHT" />
                                     </c:url>" title="Remove From Flight Plan"><img alt="Unflight" src="<c:url value="/images/unflight.png"/>"/></a>
                    </c:if>
                </c:otherwise>
                </c:choose><c:out value="${task.list.name}" />:</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                    </c:url>" title="Expand"><c:out value="${task.summary}" /></a>
        </div>
        <%--c:if test="${not empty task.details}" >
             <c:out escapeXml="false" value="${task.detailsHtmlEscaped}"/>
        </c:if--%>
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
<c:if test="${not empty task.runtimeNote}">
    <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
</c:if>
<table width="100%">
    <tr>
        <c:choose>
            <c:when test="${(not task.finished) and (not task.cancelled)}">
                <td width="80%" >
                    <hr/>
                </td>
                <td width="20%" >
                <nobr>
                    <c:choose>
                        <c:when test="${not task.inFlightPlan}">
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                        <c:param name="returnPageToken" value="${returnPageToken}" />
                                        <c:param name="taskId" value="${task.id}" />
                                        <c:param name="action" value="FLIGHT" />
                                     </c:url>" title="Append To Flight Plan"><img alt="Flight" src="<c:url value="/images/flight.png"/>"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                        <c:param name="returnPageToken" value="${returnPageToken}" />
                                        <c:param name="taskId" value="${task.id}" />
                                        <c:param name="action" value="UNFLIGHT" />
                                     </c:url>" title="Remove From Flight Plan"><img alt="Unflight" src="<c:url value="/images/unflight.png"/>"/></a>
                        </c:otherwise>
                    </c:choose>
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
                    <c:if test="${not task.delegated}">
                        <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" src="<c:url value="/images/delegate.png"/>"/></a>
                    </c:if>
                </nobr>
                </td>
            </c:when>
            <c:otherwise>
                <td>
                    <hr/>
                </td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
</c:forEach>
<br/>


<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>
