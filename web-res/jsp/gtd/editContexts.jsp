<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>


<h2><span>CONTEXTS</span> list</h2>
    <div align="right" width="80%"><a href="#" onclick="show('addCTXDiv', 'name-id'); return false">Add context</a></div>
    <div align="center">
    
    <div id="addCTXDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/nil/AddContext">
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
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addCTXDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
        <br/>
	<table width="100%">
	<tr>
		<th colspan="2">Operative</th>
	</tr>
        <c:forEach items="${contexts}" var="ctx">
	<tr>
		<td width="95%">
			<p><img src="<c:url value="/images/arrow.gif" />"/>
                        <span><a href="<c:url value="/actions/gtd/ctx/ViewContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                       </c:url>"><c:out value="${ctx.name}"/></a></span>
		</td>
		<td width="5%"> <a href="<c:url value="/actions/gtd/ctx/ToggleStateContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                       </c:url>" title="Archive"><img src="<c:url value="/images/trash.png"/>" /></a></p></td>
	</tr>
        </c:forEach>
        <c:if test="${not empty actionBean.archiveContexts}">
            <tr >
                    <th colspan="2">Archived</th>
            </tr>
            <c:forEach items="${actionBean.archiveContexts}" var="ctx" >
            <tr>
                    <td width="95%">
                            <p><img src="<c:url value="/images/arrow.gif"/>"/> <span><a href="<c:url 
                                        value="/actions/gtd/ctx/ViewContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                        </c:url>"><c:out value="${ctx.name}"/></a></span>
                    </td>
                    <td width="5%"><nobr><a href="<c:url value="/actions/gtd/ctx/ToggleStateContext">
                                                <c:param name="contextId" value="${ctx.id}" />
                                           </c:url>" title="Unarchive"><img src="<c:url value="/images/deleted.png"/>" /></a>
                                           <a href="<c:url value="/actions/gtd/ctx/EraseContext">
                                                <c:param name="contextId" value="${ctx.id}" />
                                           </c:url>" title="Delete permanently"><img src="<c:url value="/images/erase.png"/>" /></a>
                    </nobr>
                    </td>
            </tr>
            </c:forEach>
        </c:if>
	</table>
    	<br/><br/>

<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>