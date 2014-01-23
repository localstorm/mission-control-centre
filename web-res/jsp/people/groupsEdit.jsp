<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>GROUPS</span> list</h2>
    <div align="right" width="80%"><a href="#" onclick="show('addPGDiv', 'name-id'); return false">Add group</a></div>
    <div align="center">

    <div id="addPGDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/nil/AddPersonGroup">
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
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addPGDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
        <br/>
	<table width="100%">
    <c:if test="${not empty groups}">
        <tr>
            <th colspan="2">Operative</th>
        </tr>
        <c:forEach items="${groups}" var="group">
            <tr>
                <td width="95%">
                    <p><img src="<c:url value="/images/arrow.gif" />"/>
                                <span><a href="<c:url value="/actions/ppl/group/ViewPersonGroup">
                                                    <c:param name="groupId" value="${group.id}" />
                                               </c:url>"><c:out value="${group.name}"/></a></span>
                </td>
                <td width="5%"> <a href="<c:url value="/actions/ppl/group/ToggleStatePersonGroup">
                                                    <c:param name="groupId" value="${group.id}" />
                                               </c:url>" title="Archive"><img src="<c:url value="/images/trash.png"/>" /></a></p></td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${not empty actionBean.archiveGroups}">
        <tr >
                <th colspan="2">Archived</th>
        </tr>
        <c:forEach items="${actionBean.archiveGroups}" var="group" >
        <tr>
                <td width="95%">
                        <p><img src="<c:url value="/images/arrow.gif"/>"/> <span><a href="<c:url
                                    value="/actions/ppl/group/ViewPersonGroup">
                                        <c:param name="groupId" value="${group.id}" />
                                    </c:url>"><c:out value="${group.name}"/></a></span>
                </td>
                <td width="5%"><nobr><a href="<c:url value="/actions/ppl/group/ToggleStatePersonGroup">
                                            <c:param name="groupId" value="${group.id}" />
                                       </c:url>" title="Unarchive"><img src="<c:url value="/images/deleted.png"/>" /></a>
                                       <a href="<c:url value="/actions/ppl/group/ErasePersonGroup">
                                            <c:param name="groupId" value="${group.id}" />
                                       </c:url>" title="Delete permanently"><img src="<c:url value="/images/erase.png"/>" /></a>
                </nobr>
                </td>
        </tr>
        </c:forEach>
    </c:if>
</table>
<br/><br/>

<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>