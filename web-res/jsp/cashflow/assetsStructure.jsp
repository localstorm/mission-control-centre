<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSETS</span> structure</h2>

<div align="center">
    <br/>
    <img src="<c:url value="/chart/assetsStructure.png">
            <c:param name="byAssetClass" value="true" />
         </c:url>"/>
    <br/><br/>
    <img src="<c:url value="/chart/assetsStructure.png">
            <c:param name="byAssetClass" value="false" />
         </c:url>"/>
</div>


<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>