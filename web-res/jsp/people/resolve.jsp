<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>Mail List</span> generation</h2><br/>
<stripes:form action="/actions/ppl/ml/special/AddMailList" >

<div align="center">
<div id="renameDiv" width="80%">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="newname-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
        </table>
</div>
</div>
<br/>
<c:if test="${not empty pml.resolved}">
    <h2><span>Resolved Members</span> list</h2><br/>
    <c:forEach items="${pml.resolved}" var="pair" varStatus="status">
            <p><img src="<c:url value="/images/person.png"/>"/><span>
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${pair.first.id}" />
                </c:url>"><c:out value="${pair.first.fullName}" /></a>
            &nbsp;(<a href="mailto:<c:out value="${pair.second.val}"/>"><c:out value="${pair.second.val}"/></a>)
       </span></p>
        <c:choose>
            <c:when test="${status.first}"><c:set var="resolved" value="${pair.first.id}"/></c:when>
            <c:otherwise><c:set var="resolved" value="${resolved},${pair.first.id}" /></c:otherwise>
        </c:choose>
    </c:forEach>
    <br/>
</c:if>
<c:if test="${not empty pml.noEmail}">
    <h2><span>No-Email Members</span> list</h2><br/>
    <c:forEach items="${pml.noEmail}" var="person" varStatus="status">
            <p><img src="<c:url value="/images/person.png"/>"/><span>
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${person.id}" />
                </c:url>"><c:out value="${person.fullName}" /></a></span></p>

        <c:choose>
            <c:when test="${status.first}"><c:set var="noEmails" value="${person.id}"/></c:when>
            <c:otherwise><c:set var="noEmails" value="${noEmails},${person.id}" /></c:otherwise>
        </c:choose>
    </c:forEach>
    <br/>
</c:if>
<c:if test="${not empty pml.manyEmails}">
    <h2><span>Multi-Email Members</span> list</h2><br/>
    <c:forEach items="${pml.manyEmails}" var="pair" varStatus="status">
        <p><img src="<c:url value="/images/person.png"/>"/><span>
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${pair.first.id}" />
                </c:url>"><c:out value="${pair.first.fullName}" /></a></span>&nbsp;
        <stripes:select name="attributes" style="width: 30%;">
            <c:forEach items="${pair.second}" var="attr">
                <stripes:option value="${attr.id}"><c:out value="${attr.val}" /></stripes:option>
            </c:forEach>
        </stripes:select></p>
        <c:choose>
            <c:when test="${status.first}"><c:set var="manyEmails" value="${pair.first.id}"/></c:when>
            <c:otherwise><c:set var="manyEmails" value="${manyEmails},${pair.first.id}" /></c:otherwise>
        </c:choose>
    </c:forEach>
    <br/>
</c:if>

<stripes:hidden name="returnPageToken"     value="${returnPageToken}" />
<stripes:hidden name="resolvedPersonIds"   value="${resolved}" />
<stripes:hidden name="noEmailsPersonIds"   value="${noEmails}" />
<stripes:hidden name="manyEmailsPersonIds" value="${manyEmails}" />


<div align="center">
    <stripes:submit name="submit" value="Create" style="width: 7em;"/>&nbsp;
    <stripes:reset  name="reset" value="Reset" style="width: 7em;" />
</div>

</stripes:form>

<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>