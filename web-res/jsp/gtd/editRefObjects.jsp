<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>REFERENCE</span> objects</h2>
    
    <div align="right" width="80%"><a href="#" onclick="show('addRODiv', 'name-id'); return false">Add object</a></div>
    <div align="center">
    
    <div id="addRODiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/ctx/AddRefObj" >
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Context: </td>
                <td><stripes:select name="contextId" style="width: 100%;" value="${actionBean.currentContext.id}">
                        <c:forEach items="${contexts}" var="ctx">
                            <stripes:option value="${ctx.id}" label="${ctx.name}"/>
                        </c:forEach>
                    </stripes:select></td>
                <td>&nbsp;</td>
            </tr>
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
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addRODiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    
    <table width="100%">
	<c:if test="${not empty actionBean.refObjects}">
        <tr>
		<th colspan="2">Operative</th>
	</tr>
        <c:forEach items="${actionBean.refObjects}" var="ro">
            <c:choose>
                <c:when test="${color eq '#E4F1F3'}">
                    <c:set var="color" value="#F3F3F3" />
                </c:when>
                <c:otherwise>
                    <c:set var="color" value="#E4F1F3" />
                </c:otherwise>
            </c:choose>
            <tr bgColor="<c:out value="${color}" />">
                <td width="95%">
                    <p><span><c:out value="${ro.context.name}"/>:&nbsp;<a href="<c:url value="/actions/gtd/ctx/obj/ViewRefObj">
                                     <c:param name="objectId" value="${ro.id}" />
                              </c:url>"><c:out value="${ro.name}"/></a></span>
                </td>
                <td width="5%"> <a href="<c:url value="/actions/gtd/ctx/obj/ToggleStateRefObj">
                                                    <c:param name="objectId" value="${ro.id}" />
                                               </c:url>" title="Archive"><img src="<c:url value="/images/trash.png"/>" /></a></p></td>
            </tr>
        </c:forEach>
        </c:if>
        <c:if test="${not empty actionBean.archiveObjects}">
            <tr >
                    <th colspan="2">Archived</th>
            </tr>
            <c:remove var="color" />
            <c:forEach items="${actionBean.archiveObjects}" var="ro" >
                <c:choose>
                    <c:when test="${color eq '#E4F1F3'}">
                        <c:set var="color" value="#F3F3F3" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="color" value="#E4F1F3" />
                    </c:otherwise>
                </c:choose>
                <tr bgColor="<c:out value="${color}"/>">
                    <td width="95%">
                            <p><span><a href="<c:url value="/actions/gtd/ctx/obj/ViewRefObj">
                                            <c:param name="contextId" value="${ro.id}" />
                                        </c:url>"><c:out value="${ro.name}"/></a></span>
                    </td>
                    <td width="5%"><nobr><a href="<c:url value="/actions/gtd/ctx/obj/ToggleStateRefObj">
                                                <c:param name="objectId" value="${ro.id}" />
                                           </c:url>" title="Unarchive"><img src="<c:url value="/images/deleted.png"/>" /></a>
                                           <a href="<c:url value="/actions/gtd/ctx/obj/EraseRefObj">
                                                <c:param name="objectId" value="${ro.id}" />
                                           </c:url>" title="Delete permanently"><img src="<c:url value="/images/erase.png"/>" /></a>
                    </nobr>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
   </table>

    
<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>