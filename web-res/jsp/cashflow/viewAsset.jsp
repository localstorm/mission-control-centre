<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSET</span> details</h2>
<br/>
<div align="center">
<fmt:formatNumber var="buyCost" maxFractionDigits="4" minFractionDigits="0" value="${actionBean.assetResult.currentCost.buy}"/>
<fmt:formatNumber var="sellCost" maxFractionDigits="4" minFractionDigits="0" value="${actionBean.assetResult.currentCost.sell}"/>
<fmt:formatNumber var="spread" maxFractionDigits="4" minFractionDigits="0" value="${actionBean.assetResult.spread}"/>

<div align="center">
    <div id="buyDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and operationName eq 'BUY'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/cash/asset/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="BUY" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</th>
                <th colspan="2">Buying asset</th>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="buy-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="buy-comment-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Buy" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('buyDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="sellDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and operationName eq 'SELL'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/cash/asset/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="SELL" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</th>
                <th colspan="2">Selling asset</th>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="sell-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="sell-comment-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Sell" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('sellDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
</div>

<stripes:form action="/actions/cash/asset/UpdateAsset">
    <stripes:errors/>
    <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />

<table width="80%" border="0px" >
    <tr><th colspan="2" align="center"><c:out value="${actionBean.assetResult.name}"/> (<a href="<c:url value="/actions/cash/asset/ViewOperations">
                                                                                            <c:param name="assetId" value="${actionBean.assetResult.id}" />
                                                                                            <c:param name="thisMonth" value="true" />
                                                                                        </c:url>">Operations</a>)</th></tr>
    <tr bgColor="#DFFFBF">
        <td align="right">Asset name:</td>
        <td align="right"><stripes:text name="name" id="name-id" value="${actionBean.assetResult.name}" style="width: 95%"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right">Asset class:</td>
        <td align="right"><stripes:text name="assetClass" id="assetClass-id" value="${actionBean.assetResult.assetClass}" style="width: 95%"/></td>
    </tr>
    <tr bgcolor="#DFFFBF">
        <td width="50%" align="right" >Buy price (1 piece):</td>
        <td width="50%" align="right" ><stripes:text name="buy" id="buy-id" value="${buyCost}" style="width: 95%"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right">Sell price (1 piece):</td>
        <td align="right"><stripes:text name="sell" id="sell-id" value="${sellCost}" style="width: 95%"/></td>
    </tr>
    <tr bgcolor="#DFFFBF">
        <td align="right">Spread (%):</td>
        <td align="right"><c:out value="${spread}"/></td>
    </tr>
    <tr bgcolor="#FBFFBD">
        <td align="right" >Total amount (pieces):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="5" minFractionDigits="2" value="${actionBean.assetResult.amount}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Net wealth:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.netWealth}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Investment amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.investmentsCost}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Revenu amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.revenuAmount}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Investment return:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.balance}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Use in ROI:</td>
        <td align="right" ><stripes:checkbox name="usedInBalance" checked="${actionBean.assetResult.valuable.usedInBalance}" /></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Is liability:</td>
        <td align="right" ><stripes:checkbox name="debt" checked="${actionBean.assetResult.valuable.debt}" /></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td colspan="2" align="center"><stripes:submit name="fix" value="Correct" onclick="return confirm('Are you sure?');"/>&nbsp;<stripes:submit name="update" value="Update"/>&nbsp;<stripes:reset name="rst" value="Reset" /></td>
    </tr>
    
    <tr bgcolor="#FBFFBD">
        <td colspan="2">
        <table width="100%" border="0px">
            <tr>
                <td align="left" border="0px">
                    <a href="#" onclick="show('buyDiv', 'buy-amount-id'); hide('sellDiv'); return false"><img src="<c:url value="/images/buy.png"/>"/></a>
                    <a href="#" onclick="show('sellDiv', 'sell-amount-id'); hide('buyDiv');  return false"><img src="<c:url value="/images/sell.png"/>"/></a>
                </td>
                <td align="right"  border="0px">
                    <a href="<c:url value="/actions/cash/asset/ViewAssetCostHistory">
                                <c:param name="assetId" value="${actionBean.assetResult.id}"/>
                        </c:url>"><img src="<c:url value="/images/history.png"/>"/></a>
                </td>
            </tr>
        </table>
        </td>
    </tr>
</table>
</stripes:form>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>