<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<tr>
    <td width="18px" valign="top"><img src="<c:url value="/images/ml.png"/>"/></td>
    <td><a href="<c:url value="/actions/ppl/ml/ViewMailList">
                    <c:param name="mailListId" value="${param.id}"/>
                 </c:url>"><c:out value="${param.name}" /></a></td>
</tr>