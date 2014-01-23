<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>MARKET</span> prices</h2>
<br/>
<div align="center">
<table width="80%" border="0px" >
    <tr>
        <th>Symbol</th>
        <th>Buy price</th>
        <th>Sell price</th>
        <th>Spread (%)</th>
    </tr>
<c:forEach items="${actionBean.symbols}" var="symbol" varStatus="loop">
    <c:choose>
        <c:when test="${loop.index % 2 eq 0}">
            <c:set var="color" value="#F3F3F3"/>
        </c:when>
        <c:otherwise>
            <c:set var="color" value="#E4F1F3"/>
        </c:otherwise>
    </c:choose>
    <tr bgcolor="<c:out value="${color}"/>">
        <td width="40%" align="left" ><c:out value="${symbol.key}"/></td>
        <td width="20%" align="left" ><fmt:formatNumber maxFractionDigits="4" minFractionDigits="2" value="${symbol.value.buy}"/></td>
        <td width="20%" align="left" ><fmt:formatNumber maxFractionDigits="4" minFractionDigits="2" value="${symbol.value.sell}"/></td>
        <td width="20%" align="left" ><fmt:formatNumber maxFractionDigits="4" minFractionDigits="2" value="${symbol.value.spread}"/></td>
    </tr>
</c:forEach>
</table>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>