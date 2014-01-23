<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

</div>
<div id="bodyRightPan">
  	<h2><span>Groups</span> pane</h2>
	<p class="more"><a href="<c:url value="/actions/ppl/nil/EditPersonGroups"/>">EDIT</a></p>
        <table class="contexts">
            <c:forEach items="${groups}" var="group" >
            <tr>
                <td width="18px" valign="top"><img src="<c:url value="/images/group.png"/>"/></td>
                <td><a href="<c:url value="/actions/ppl/group/ViewPersonGroup">
                       <c:param name="groupId" value="${group.id}" />
                     </c:url>"><c:out value="${group.name}"/></a></td>
            </tr>
            </c:forEach>
        </table>            
	
        <%--h2><span>Search</span> pane</h2>
            <table class="reports">
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/search.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/nil/SearchTasks"/>">Search people</a></td>
                </tr>
            </table--%>
</div>
