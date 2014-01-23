<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>OPERATIONS</span> history</h2>
<div align="right" width="80%"><a href="<c:url value="/actions/cash/asset/ViewAsset">
                                            <c:param name="assetId" value="${actionBean.asset.id}"/>
                                  </c:url>"><img src="<c:url value="/images/parent.png"/>" /></a>
                                      <a href="<c:url value="/actions/cash/asset/ViewOperations">
                                            <c:param name="assetId" value="${actionBean.asset.id}"/>
                                      </c:url>">View full history</a>
</div>
<br/>
<div align="center">
<table width="90%" border="0px" >
    <tr bgcolor="#FFFFFF">
        <th colspan="5"><c:out value="${actionBean.asset.name}"/></th>
    </tr>
    <tr bgcolor="#DFFFBF">
        <th>Type</th>
        <th>Amount</th>
        <th width="50%">Comment</th>
        <th>Date</th>
        <th>Action</th>
    </tr>
<c:forEach items="${actionBean.operations}" var="op">
    <tr bgcolor="#FBFFBD">
        <td width="5%" align="center" ><img src="<c:url value="/images/op_${op.type}.png"/>"/></td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${op.amount}"/></td>
        <td align="right" ><c:out value="${op.comment}" /></td>
        <td align="right" ><fmt:formatDate type="date" value="${op.operationDate}" /></td>
        <td align="center" ><a onclick="return confirm('Are you sure?');" title="Revoke operation" href="<c:url value="/actions/cash/op/RevokeOperation">
                                        <c:param name="operationId" value="${op.id}" />
                                        <c:param name="returnPageToken" value="${returnPageToken}" />Щ
                                     </c:url>"><img src="<c:url value="/images/revoke.png"/>" /></a></td>
    </tr>
</c:forEach>
</table>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>