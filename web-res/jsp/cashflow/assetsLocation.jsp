<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSETS</span> structure</h2>

<div align="center">
    <c:forEach items="${actionBean.classes}" var="clazz" varStatus="loop">
        <br/>
        <img src="<c:url value="/chart/assetsLocation.png?class=${clazz}"/>"/>
        <br/>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>