<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSET</span> price history</h2>
<div align="center">
    <br/>
    <img src="<c:url value="/chart/assetCost.png">
        <c:param name="assetId" value="${actionBean.assetResult.id}" />
        <c:param name="name" value="Asset price weekly changes" />
        <c:param name="period" value="7" />
    </c:url>"/><br/><br/>
    <img src="<c:url value="/chart/assetCost.png">
        <c:param name="assetId" value="${actionBean.assetResult.id}" />
        <c:param name="name" value="Asset price monthly changes" />
        <c:param name="period" value="30" />
    </c:url>"/><br/><br/>
    <img src="<c:url value="/chart/assetCost.png">
        <c:param name="name" value="Asset price changes history" />
        <c:param name="assetId" value="${actionBean.assetResult.id}" />
    </c:url>"/>
</div>


<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>