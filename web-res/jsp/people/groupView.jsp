<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/clipboard.jsp" %>
<h2><span>GROUP</span> view</h2>
<div align="right" ><a href="#" onclick="show('addPersonDiv', 'name-id'); hide('renameDiv'); return false">Add person</a>
                            (<a href="#" onclick="show('renameDiv', 'newname-id'); hide('addPersonDiv'); return false"><c:out value="${actionBean.group.name}"/></a>)</div>

    <div align="center">

    <div id="addPersonDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/group/AddPerson" >
        <stripes:errors/>
        <stripes:hidden name="groupId" value="${actionBean.group.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>First Name: </td>
                <td><stripes:text name="firstName" id="name-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Last Name: </td>
                <td><stripes:text name="lastName" id="lname-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Patronymic Name: </td>
                <td><stripes:text name="patronymicName" id="pname-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addPersonDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
<div id="renameDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and not empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/group/RenamePersonGroup" >
        <stripes:errors/>
        <stripes:hidden name="groupId" value="${actionBean.group.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="newname-id" style="width: 100%;" value="${actionBean.group.name}" /></td>
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
                    <stripes:submit name="submit" value="Rename" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('renameDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
</div>
<br/><br/>
<c:forEach items="${actionBean.persons}" var="person">
    <p><img src="<c:url value="/images/person.png"/>"/><a href="<c:url value="/actions/ppl/group/person/ClipPerson">
        <c:param name="personId" value="${person.id}" />
    </c:url>"><img
    src="<c:url value="/images/add2ml.png"/>"/></a><span>
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${person.id}" />
            </c:url>"><c:out value="${person.fullName}" /></a>
       </span></p>
</c:forEach>

<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>