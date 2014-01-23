<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/clipboard.jsp" %>
<h2><span>LIST</span> tasks</h2>
<div align="right" ><a href="<c:url value="/actions/gtd/ctx/ViewContext" >
                               <c:param name="contextId" value="${actionBean.listResult.context.id}" />
                             </c:url>" title="Go to parent"><img src="<c:url value="/images/parent.png" />" /></a>&nbsp;<a href="#" onclick="show('addTaskDiv', 'summary-id'); hide('renameDiv'); return false">Add task</a>
                            (<a href="#" onclick="show('renameDiv', 'newname-id'); hide('addTaskDiv'); return false"><c:out value="${actionBean.listResult.name}"/></a>)</div> 


    <div align="center">
    
    <div id="addTaskDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/ctx/list/AddTask" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="${actionBean.listResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Summary: </td>
                <td><stripes:text name="summary" id="summary-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Effort: </td>
                <td>
                    <stripes:select name="effort" value="3" style="width: 100%">
                        <c:forEach items="${actionBean.efforts}" var="effort">
                            <stripes:option value="${effort.effort}"><c:out value="${effort.latinName}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Flight: </td>
                <td>
                    <stripes:checkbox name="flight" checked="false" />
                </td>
                <td>&nbsp;</td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addTaskDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
<div id="renameDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and not empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/ctx/list/RenameList" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="${actionBean.listResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="newname-id" style="width: 100%;" value="${actionBean.listResult.name}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Rename" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('renameDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
</div>

<table width="100%">
<c:if test="${not empty actionBean.tasks}" >
    <tr>
        <th colspan="2">Operative</th>
    </tr>
    <c:forEach items="${actionBean.tasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
        <div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
            <stripes:form action="/actions/gtd/ctx/list/task/ResolveTask" >
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
                        <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="COPY" />
                                 </c:url>" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" /></a>
                    <c:choose>
                        <c:when test="${not task.inFlightPlan}">
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="Flight" src="<c:url value="/images/flight.png"/>"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="UNFLIGHT" />
                             </c:url>" title="Remove From Flight Plan"><img alt="Unflight" src="<c:url value="/images/unflight.png"/>"/></a>
                        </c:otherwise>
                    </c:choose>
                        <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="FINISH" />
                                 </c:url>" title="Finish"><img alt="finish" src="<c:url value="/images/finish.png"/>"/></a>
                        <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="CANCEL" />
                                 </c:url>" title="Cancel"><img alt="cancel" src="<c:url value="/images/cancel.png"/>"/></a>
                        <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" src="<c:url value="/images/delegate.png"/>"/></a>
                    </nobr>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
    </c:forEach>
</c:if>
<c:if test="${not empty actionBean.awaitedTasks}">
    <tr>
        <th colspan="2">Awaited</th>
    </tr>
    <c:forEach items="${actionBean.awaitedTasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNDELEGATE" />
                        </c:url>" title="Not delegated"><img src="<c:url value="/images/delegated.png"/>" /></a>
              <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}"/></a>
            <c:if test="${not empty task.runtimeNote}">
                <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
            </c:if>
            <hr/>
        </td>
    </tr>
    </c:forEach>
</c:if>

<c:if test="${not empty actionBean.archiveTasks}" >
<tr>
    <th colspan="2">Archive</th>
</tr>

<c:forEach items="${actionBean.archiveTasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNRESOLVE" />
                        </c:url>" title="<c:choose><c:when test="${task.finished}">Not finished</c:when><c:when test="${task.cancelled}">Not cancelled</c:when><c:otherwise>Unresolve</c:otherwise></c:choose>">
                        <img src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>" /></a>
              <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}"/></a><hr/></p>
        </td>
    </tr>
</c:forEach>
</c:if>	
</table>

<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>