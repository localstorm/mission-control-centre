<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>OLD</span> tasks</h2>
 <table width="100%">
    <tr>
        <td align="left">
            <jsp:include page="/WEB-INF/jsp/includes/gtd/ctxFilter.jsp">
                <jsp:param name="returnPageToken" value="${returnPageToken}" />
            </jsp:include>
        </td>
    </tr>
 </table>

<c:forEach items="${contexts}" var="ctx">
<c:if test="${not empty actionBean.tasksResult[ctx.id]}">
    <table width="100%">
        <tr>
            <th colspan="2"><c:out value="${ctx.name}" /></th>
        </tr>
    </table>
</c:if>
<c:forEach items="${actionBean.tasksResult[ctx.id]}" var="task">
    <p><span><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<c:out value="${task.list.name}" />:</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                    </c:url>" title="Expand"><c:out value="${task.summary}" /></a>
        </div>

        <c:if test="${not empty task.details}" >
            &nbsp;<a href="#" onclick="$('#details_<c:out value="${task.id}"/>').animate({height:'toggle',opacity:'toggle'}, 'fast'); return false">Details (click to view/hide)</a>
            <div align="justify" id="details_<c:out value="${task.id}"/>" style="display: none;" >
                <c:out escapeXml="false" value="${task.detailsHtmlEscaped}"/>
            </div>
        </c:if>
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
            <c:if test="${not task.inFlightPlan}">
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" src="<c:url value="/images/flight.png"/>"/></a>
            </c:if>
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
<c:if test="${not empty actionBean.tasksResult[ctx.id]}">
    <br/><br/>
</c:if>
</c:forEach>
<br/>


<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>