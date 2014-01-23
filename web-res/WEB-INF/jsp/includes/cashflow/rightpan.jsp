<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

</div>
<div id="bodyRightPan">
  	<h2><span>Assets</span> pane</h2>
	<p class="more"><a href="<c:url value="/actions/cash/nil/EditAssets"/>">EDIT</a></p>
        <table class="contexts">
            <c:forEach items="${assets}" var="asset" >
            <tr>
                <td width="18px" valign="top"><img src="<c:url value="/images/asset.png"/>"/></td>
                <td><a href="<c:url value="/actions/cash/asset/ViewAsset">
                       <c:param name="assetId" value="${asset.id}" />
                     </c:url>"><c:out value="${asset.name}"/></a></td>
            </tr>
            </c:forEach>
        </table>            
	
        <h2><span>Reports</span> pane</h2>
            <table class="reports">
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/cash/nil/MarketPricesReport"/>">Market prices</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/cash/nil/AssetsStructureReport"/>">Assets structure</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/cash/nil/NetWorthHistoryReport"/>">Net worth history</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/cash/nil/RoiHistoryReport"/>">ROI report</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/cash/nil/DebtHistoryReport"/>">Debt history</a></td>
                </tr>
            </table>
</div>
