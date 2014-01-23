<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSET</span> list</h2>
    <div align="right" width="80%"><a href="#" onclick="show('addAssDiv', 'name-id'); return false">Add asset</a></div>
    <div align="center">
    
    <div id="addAssDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/cash/nil/AddAsset">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="name-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
             <tr>
                <td>&nbsp;</td>
                <td>Class: </td>
                <td><stripes:text name="assetClass" id="assetClass-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Cost (buy): </td>
                <td><stripes:text name="buy" id="buy-id" style="width: 100%;" value="0"/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Cost (sell): </td>
                <td><stripes:text name="sell" id="sell-id" style="width: 100%;" value="0"/></td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addAssDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
        <br/>
	<table width="100%">

    <c:if test="${not empty assets}">
        <tr>
            <th colspan="2">Operative</th>
        </tr>
    
        <c:forEach items="${assets}" var="asset">
        <tr>
            <td width="95%">
                <p><img src="<c:url value="/images/arrow.gif" />"/>
                            <span><a href="<c:url value="/actions/cash/asset/ViewAsset">
                                                <c:param name="assetId" value="${asset.id}" />
                                           </c:url>"><c:out value="${asset.name}"/></a></p></span>
            </td>
            <td width="5%">
                <c:if test="${asset.amount eq 0}">
                <a href="<c:url value="/actions/cash/asset/ToggleStateAsset">
                                                <c:param name="assetId" value="${asset.id}" />
                                           </c:url>" title="Archive"><img src="<c:url value="/images/trash.png"/>" /></a>
                </c:if>
            </td>
        </tr>
        </c:forEach>
    </c:if>
    <c:if test="${not empty actionBean.archiveAssets}">
        <tr>
            <th colspan="2">Archive</th>
        </tr>

        <c:forEach items="${actionBean.archiveAssets}" var="asset">
         <tr>
                    <td width="95%">
                            <p><img src="<c:url value="/images/arrow.gif"/>"/> <span><a href="<c:url
                                        value="/actions/cash/asset/ViewAsset">
                                            <c:param name="assetId" value="${asset.id}" />
                                        </c:url>"><c:out value="${asset.name}"/></a></span>
                    </td>
                    <td width="5%"><c:if test="${asset.amount eq 0}">
                                        <nobr><a href="<c:url value="/actions/cash/asset/ToggleStateAsset">
                                                <c:param name="assetId" value="${asset.id}" />
                                           </c:url>" title="Unarchive"><img src="<c:url value="/images/deleted.png"/>" /></a>
                                           <a href="<c:url value="/actions/cash/asset/EraseAsset">
                                                <c:param name="assetId" value="${asset.id}" />
                                           </c:url>" title="Delete permanently"><img src="<c:url value="/images/erase.png"/>" /></a>
                                        </nobr>
                                    </c:if>
                    </td>
            </tr>
        </c:forEach>
    </c:if>

	</table>
	<br/><br/>


<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>