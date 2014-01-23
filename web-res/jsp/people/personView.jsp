<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>PERSON</span> view</h2>
<div align="right" >
    <a href="<c:url value="/actions/ppl/group/ViewPersonGroup">
        <c:param name="groupId" value="${actionBean.person.group.id}" />
    </c:url>" title="Go to parent"><img src="<c:url value="/images/parent.png" />" /></a>
    <c:if test="${not empty actionBean.joinableMailLists and not empty actionBean.emails}">
        <a href="#" onclick="show('joinMLDiv', 'ml-id'); hide('editPersonDiv'); return false">Subscribe</a>
    </c:if>
    <a href="#" onclick="show('editPersonDiv', 'name-id'); hide('joinMLDiv'); return false">Edit person</a>
    <a href="<c:url value="/actions/ppl/group/person/RemovePerson">
        <c:param name="personId" value="${actionBean.person.id}" />
    </c:url>" onclick="return confirm('Are you sure?');">Remove person</a>

</div>

<div align="center">
    <div id="editPersonDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and not empty updateForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/group/person/UpdatePerson" name="editForm">
        <stripes:errors/>
        <stripes:hidden name="personId" value="${actionBean.person.id}" />
        
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>First Name: </td>
                <td><stripes:text name="firstName" id="name-id" style="width: 100%;" value="${actionBean.person.name}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Last Name: </td>
                <td><stripes:text name="lastName" id="lname-id" style="width: 100%;" value="${actionBean.person.lastName}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Patronymic Name: </td>
                <td><stripes:text name="patronymicName" id="pname-id" style="width: 100%;" value="${actionBean.person.patronymicName}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Person Group: </td>
                <td>
                    <stripes:select name="groupId" value="${actionBean.person.group.id}" style="width: 100%">
                        <c:forEach items="${groups}" var="group">
                            <stripes:option value="${group.id}" ><c:out escapeXml="false" value="${group.name}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Birth date: </td>
                <fmt:formatDate value="${actionBean.person.birthDate}" pattern="dd.MM.yyyy" var="birthDate" />
                <td><stripes:text readonly="true" name="birthDate" id="d1" size="10" value="${birthDate}"/><img onclick="showCalendar('d1', '%d.%m.%Y', false, true);" border="0px" src="<c:url value="/images/calendar.png"/>" />&nbsp;<img onclick="document.editForm.birthDate.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" /></td>
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
                    <stripes:submit name="submit" value="Ok" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('editPersonDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="joinMLDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and not empty joinForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/ml/person/attr/JoinMailList" name="joinForm">
        <stripes:errors/>
        <stripes:hidden name="personId" value="${actionBean.person.id}" />

        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Mail List: </td>
                <td>
                    <stripes:select name="mailListId" id="ml-id" style="width: 100%">
                        <c:forEach items="${actionBean.joinableMailLists}" var="ml">
                            <stripes:option value="${ml.id}" ><c:out escapeXml="false" value="${ml.name}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>E-mail: </td>
                <td>
                    <stripes:select name="attributeId" id="attr-id" style="width: 100%">
                        <c:forEach items="${actionBean.emails}" var="attr">
                            <stripes:option value="${attr.id}" ><c:out escapeXml="false" value="${attr.val}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </td>
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
                    <stripes:submit name="submit" value="Ok" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('joinMLDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
</div>
<br/>
<c:if test="${actionBean.needEmail}">
<div align="center" class="warn">
    <img src="<c:url value="/images/warning2.png"/>"/>&nbsp;<b>Specify e-mail attribute for this person, please.</b>
</div>
</c:if>
<br/>
<div align="center" style="padding: 4px; border: 1px dotted; margin: 0px 60px 0px 60px; background:#FFFDCD;">
<p><span><img src="<c:url value="/images/person.png"/>"/> <c:out value="${actionBean.person.fullName}" />
<c:if test="${not empty actionBean.person.birthDate}">
    (<fmt:formatDate value="${actionBean.person.birthDate}"/>)
</c:if></span><hr/></p>
<table width="100%" >
    <c:forEach items="${actionBean.attributes}" var="attribute">
    <tr>
		<td width="5%" >
			<nobr><a href="<c:url value="/actions/ppl/group/person/attr/RemovePersonAttribute">
                <c:param name="personId"    value="${actionBean.person.id}"/>
                <c:param name="attributeId" value="${attribute.id}"/>
            </c:url>"><img src="<c:url value="/images/cleanup.png"/>"/></a><img src="<c:url value="/images/attr/${attribute.type.token}.png"/>"/></nobr>
		</td>
		<td width="20%" align="left" valign="top">
			<c:out value="${attribute.type.name}" />:
		</td>
		<td align="left">
            <c:choose>
                <c:when test="${attribute.type.viewType eq 'text'}">
                    <c:out value="${attribute.val}"/>
                </c:when>
                <c:when test="${attribute.type.viewType eq 'href'}">
                    <a target="_blank" href="<c:out value="${attribute.val}"/>"><c:out value="${attribute.val}"/></a>
                </c:when>
                <c:when test="${attribute.type.viewType eq 'mailto'}">
                    <a href="mailto:<c:out value="${attribute.val}"/>"><c:out value="${attribute.val}"/></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${attribute.val}"/>
                </c:otherwise>
            </c:choose>
        </td>
	</tr>
    </c:forEach>
	<tr>
		<td colspan="3">
			<hr/>
		</td>
	</tr>
    <stripes:form action="/actions/ppl/group/person/AddPersonAttribute">
        <c:if test="${not empty actionBean.context.validationErrors}">
            <tr>
                <td colspan="3"><stripes:errors/></td>
            </tr>
        </c:if>
        <stripes:hidden name="personId" value="${actionBean.person.id}" />
        <tr valign="top">
            <td width="25%" colspan="2">
                <stripes:select name="typeId">
                    <c:forEach items="${actionBean.attributeTypes}" var="type">
                        <stripes:option value="${type.id}"><c:out value="${type.name}"/></stripes:option>
                    </c:forEach>
                </stripes:select>
            </td>

            <td align="left"><stripes:text style="width: 98%;" name="value"/></td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <stripes:submit name="submit" value="Add"/>&nbsp;
                <stripes:reset name="reset"   value="Reset"/>
            </td>
        </tr>
    </stripes:form>
</table>
</div>





<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>