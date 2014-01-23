<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/dashhdr.jsp" %>

<div id="dashBirthdaysPan">
    <h2><span>Birthdays</span> pane</h2>
    <c:if test="${not empty peopleDashReport.redPersons}">
        <div id="redDashPane">
            <c:forEach items="${peopleDashReport.redPersons}" var="pw">
                <p><img src="<c:url value="/images/person.png" />" /> <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                    <c:param name="personId" value="${pw.id}"/>
                </c:url>"><c:out value="${pw.shortName}"/></a>
                <c:choose>
                    <c:when test="${pw.remains>0}">(+<c:out value="${pw.remains}"/>)</c:when>
                    <c:otherwise><img src='<c:url value="/images/today.png"/>' /></c:otherwise>
                </c:choose>
                </p>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty peopleDashReport.yellowPersons}">
        <div id="yellowDashPane">
                <c:forEach items="${peopleDashReport.yellowPersons}" var="pw">
                    <p><img src="<c:url value="/images/person.png" />" /> <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                    <c:param name="personId" value="${pw.id}"/>
                </c:url>"><c:out value="${pw.shortName}"/></a> (+<c:out value="${pw.remains}"/>)</p>
                </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty peopleDashReport.greenPersons}">
        <div id="greenDashPane">
                <c:forEach items="${peopleDashReport.greenPersons}" var="pw">
                    <p><img src="<c:url value="/images/person.png" />" /> <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                    <c:param name="personId" value="${pw.id}"/>
                </c:url>"><c:out value="${pw.shortName}"/></a> (+<c:out value="${pw.remains}"/>)</p>
                </c:forEach>
        </div>
    </c:if>
</div>

<c:if test="${not empty inbox}">
    <div id="dashIbxPan">
        <h2><span>GTD Inbox</span> pane</h2>
        <p><img src="<c:url value="/images/inbox.png"/>" border="0"/>
           <a href="<c:url value="/actions/gtd/nil/ViewInbox"/>">Inbox messages (<c:out value="${fn:length(inbox)}" />)</a>
        </p>
    </div>
</c:if>

<div id="dashGTDPan">
    <h2><span>GTD Battle Map</span> pane</h2>
	<div align="center">
		<table class="dash">
			<tr class="dashHdr" ><th colspan="13" class="normalGtdTh">Consolidated Tasks Report</th></tr>
			<tr>
                <th width="28%" class="normalGtdTh">Context name</th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loex.png"/>" /></th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loe1.png"/>" /></th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loe2.png"/>" /></th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loe3.png"/>" /></th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loe4.png"/>" /></th>
                <th width="6%" class="specialGtdTh"><img src="<c:url value="/images/loe5.png"/>" /></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/awaited_dash.png"/>" /></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/flight_dash.png"/>" /></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/repeated.png"/>" /></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/redline.png"/>" /></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/deadline.png" />"/></th>
                <th width="6%" class="normalGtdTh"><img src="<c:url value="/images/check.png" />"/></th>
            </tr>
            <c:set var="color" value="#FFFFFF" />
            <c:forEach items="${gtdDashReport.rows}" var="row">
                <tr bgcolor="<c:out value="${color}" />">
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/ViewContext">
                                                <c:param name="contextId" value="${row.contextId}" />
                                            </c:url>';"><c:out value="${row.contextName}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="PENDING" />
                                            </c:url>';"><c:out value="${row.pending}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="ELEMENTARY" />
                                            </c:url>';"><c:out value="${row.elementary}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="EASY" />
                                            </c:url>';"><c:out value="${row.easy}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="MEDIUM" />
                                            </c:url>';"><c:out value="${row.medium}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="DIFFICULT" />
                                            </c:url>';"><c:out value="${row.difficult}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="VERY_DIFFICULT" />
                                            </c:url>';"><c:out value="${row.veryDifficult}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="AWAITED" />
                                            </c:url>';"><c:out value="${row.awaited}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="FLIGHT" />
                                            </c:url>';"><c:out value="${row.flightPlan}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="HINTED" />
                                            </c:url>';"><c:out value="${row.hinted}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="REDLINE" />
                                            </c:url>';"><c:out value="${row.red}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="DEADLINE" />
                                            </c:url>';"><c:out value="${row.dead}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/gtd/ctx/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="FIN" />
                                            </c:url>';"><c:out value="${row.done}" /></td>
                </tr>
                <c:choose>
                    <c:when test="${color eq '#FFFFFF'}" >
                        <c:set var="color" value="#EEEEFF" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="color" value="#FFFFFF" />
                    </c:otherwise>
                </c:choose>
            </c:forEach>
			<tr>
                <th class="normalGtdTh">Total</th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.pending}" /></th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.elementary}" /></th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.easy}" /></th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.medium}" /></th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.difficult}" /></th>
                <th class="specialGtdTh"><c:out value="${gtdDashReport.totals.veryDifficult}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.awaited}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.flightPlan}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.hinted}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.red}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.dead}" /></th>
                <th class="normalGtdTh"><c:out value="${gtdDashReport.totals.done}" /></th>
            </tr>
		</table>
        <c:if test="${gtdDashReport.totals.done>0 or 
                      gtdDashReport.totals.red>0  or
                      gtdDashReport.totals.dead>0 or
                      gtdDashReport.totals.flightPlan>0}">
        <div align="left" class="dashToolBox">
            <c:if test="${gtdDashReport.totals.done>0}"><img src="<c:url value="/images/cleanup.png"/>" />&nbsp;<a onclick="return confirm('Are you sure?');" href="<c:url value="/actions/gtd/nil/CleanupFinishedTasks" />" >Remove finished tasks</a> (<c:out value="${gtdDashReport.totals.done}"/>)&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
            <c:if test="${gtdDashReport.totals.red>0 or gtdDashReport.totals.dead>0}"><img src="<c:url value="/images/warning.png"/>" />&nbsp;<a href="<c:url value="/actions/gtd/nil/DeadlineReport" >
                <c:param name="noFilter" value="true" />
            </c:url>" >Some tasks need your attention</a> (<c:out value="${gtdDashReport.totals.dead+gtdDashReport.totals.red}"/>)&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
            <c:if test="${gtdDashReport.totals.flightPlan>0}"><img src="<c:url value="/images/flight_dash.png"/>" />&nbsp;<a href="<c:url value="/actions/gtd/nil/ViewFlightPlan">
                <c:param name="noFilter" value="true" />
            </c:url>" >Some tasks were appointed</a> (<c:out value="${gtdDashReport.totals.flightPlan}"/>)</c:if>
 		</div>
        </c:if>
    </div>
</div>

<c:if test="${not empty firedHintsReport.fired}">
    <div id="dashGTD2Pan">
        <h2><span>Hinted Tasks</span> pane</h2>
        <c:forEach items="${firedHintsReport.fired}" var="taskStub">
            <p><a href="<c:url value="/actions/gtd/ctx/list/task/UnhintTask">
                <c:param name="taskId" value="${taskStub.id}" />
                <c:param name="returnPageToken" value="${returnPageToken}" />
            </c:url>"><img src="<c:url value="/images/unhint.png"/>"/></a><a href="<c:url value="/actions/gtd/ctx/list/ViewList">
                <c:param name="listId" value="${taskStub.listId}" />
            </c:url>"><img src="<c:url value="/images/gotoList.png"/>" /></a>&nbsp;<a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                <c:param name="taskId" value="${taskStub.id}" />
                <c:param name="returnPageToken" value="${returnPageToken}" />
            </c:url>"><c:out value="${taskStub.summary}"/></a></p>
        </c:forEach>
    </div>
</c:if>

<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>


