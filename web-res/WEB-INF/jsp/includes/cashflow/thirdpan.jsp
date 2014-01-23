<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<div id="bodyThirdPan">
    <h2><span>Targets</span> pane</h2>
    <p class="more"><a href="<c:url value="/actions/cash/nil/EditTargets"/>">EDIT</a></p>
    <table class="refobjs">
        <c:forEach items="${targets}" var="target">
            <jsp:include page="/WEB-INF/jsp/includes/cashflow/target.jsp">
                <jsp:param name="id"   value="${target.id}"   />
                <jsp:param name="name" value="${target.name}" />
            </jsp:include>
        </c:forEach>
    </table>                
</div>
