<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>TASKS</span> search</h2>
    <div align="right" width="80%"><a href="#" onclick="show('tsDiv', 'text-id'); return false">Search again</a></div>
    <div align="center">

    <div id="tsDiv" width="80%" style="display: <c:choose>
             <c:when test="${not actionBean.found}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/nil/SubmitTaskSearch" focus="text">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Text: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" value="${actionBean.text}" /></td>
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
                    <stripes:submit name="submit" value="Search" style="width: 7em;"/>
                    <c:if test="${actionBean.found}">
                        <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('tsDiv'); return false" />
                    </c:if>

                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    <br/>
    <c:if test="${actionBean.found}">

    <c:if test="${not empty actionBean.operativeTasks}" >
        <table width="100%">
            <tr>
                <th>Operative</th>
            </tr>
        </table>
    <c:forEach items="${actionBean.operativeTasks}" var="task" varStatus="status">
        <p><span><c:choose>
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
                    </c:choose><c:out value="${task.list.context.name}" />, <c:out value="${task.list.name}" />:</span><br/>
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
        <table width="100%">
            <tr>
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
            </tr>
        </table>
    </c:forEach>
</c:if>


<c:if test="${not empty actionBean.awaitedTasks}">
    <table width="100%">
        <tr>
            <th>Awaited</th>
        </tr>
    </table>
    <c:forEach items="${actionBean.awaitedTasks}" var="task" varStatus="status">
            <p><span><a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="UNDELEGATE" />
                            </c:url>" title="Not delegated"><img src="<c:url value="/images/delegated.png"/>"/></a>
                      <c:out value="${task.list.context.name}" />, <c:out value="${task.list.name}" />:</span><br/>
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
            </tr>
        </table>
    </c:forEach>
</c:if>

<c:if test="${not empty actionBean.archiveTasks}" >
<table width="100%">
    <tr>
        <th>Archive</th>
    </tr>
</table>
<c:forEach items="${actionBean.archiveTasks}" var="task" varStatus="status">
    <p><span><a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
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
                <c:out value="${task.list.context.name}" />, <c:out value="${task.list.name}" />:</span><br/>
                <div align="center">
                    <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="returnPageToken" value="${returnPageToken}" />
                            </c:url>" title="Expand"><c:out value="${task.summary}" /></a>
                </div>
                <c:if test="${not empty task.details}" >
                     <c:out escapeXml="false" value="${task.detailsHtmlEscaped}"/>
                </c:if>
        </p>
        <c:if test="${not empty task.runtimeNote}">
            <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
        </c:if>
        <table width="100%">
            <tr>
                <td>
                    <hr/>
                </td>
            </tr>
        </table>
</c:forEach>
</c:if>
</c:if>

<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>