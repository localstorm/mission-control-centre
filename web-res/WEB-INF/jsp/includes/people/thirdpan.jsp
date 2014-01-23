<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<div id="bodyThirdPan">
    <h2><span>Mail</span> lists</h2>
    <p class="more"><a href="<c:url value="/actions/ppl/nil/EditMailLists"/>">EDIT</a></p>
    <table class="refobjs">
        <c:forEach items="${mailLists}" var="ml">
            <jsp:include page="/WEB-INF/jsp/includes/people/ml.jsp">
                <jsp:param name="id"   value="${ml.id}"   />
                <jsp:param name="name" value="${ml.name}" />
            </jsp:include>
        </c:forEach>
    </table>
</div>
