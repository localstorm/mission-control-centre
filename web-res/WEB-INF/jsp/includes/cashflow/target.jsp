<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<tr>
    <td width="18px" valign="top"><img src="<c:url value="/images/refobj.png"/>"/></td>
    <td><a href="<c:url value="/actions/cash/target/ViewTarget">
                    <c:param name="targetId" value="${param.id}"/>
                 </c:url>"><c:out value="${param.name}" /></a></td>
</tr>