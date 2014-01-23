<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/clipboard.jsp" %>
<h2><span>People</span> control</h2>
    <%--table width="100%">
        <tr>
            <td align="left">
                <jsp:include page="/WEB-INF/jsp/includes/gtd/ctxFilter.jsp">
                    <jsp:param name="returnPageToken" value="${returnPageToken}" />
                </jsp:include>
            </td>
                
            <td align="right">
                <c:if test="${(not empty actionBean.flightPlanTasks) or (not empty actionBean.awaitedFlightPlanTasks) or (not empty actionBean.archiveFlightPlanTasks)}">
                    <a onclick="return confirm('Are you sure?');" href="<c:url value="/actions/gtd/nil/UtilizeFlightPlan" />" title="Utilize &amp; build new"><img src="<c:url value="/images/utilize.gif"/>" /></a>
                </c:if>
            </td>
        </tr>
    </table--%>
<br/>
 
<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>