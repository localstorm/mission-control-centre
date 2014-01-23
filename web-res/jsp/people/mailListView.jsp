<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>Mail List</span> view</h2>
<div align="right" >(<a href="#" onclick="show('renameDiv', 'newname-id'); return false"><c:out value="${actionBean.mailList.name}"/></a>)</div>
<div align="center">
<div id="renameDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ppl/ml/RenameMailList" >
        <stripes:errors/>
        <stripes:hidden name="mailListId" value="${actionBean.mailList.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="newname-id" style="width: 100%;" value="${actionBean.mailList.name}" /></td>
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
<br/>
<c:if test="${not empty actionBean.expired}">
<div align="center" class="warn">
    <img src="<c:url value="/images/warning2.png"/>"/>&nbsp;<b>Personal e-mail was changed or removed for these persons:</b>
        <c:forEach items="${actionBean.expired}" var="person">
            <p><img src="<c:url value="/images/person.png"/>"/><span>
                    <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                        <c:param name="personId" value="${person.id}" />
                    </c:url>"><c:out value="${person.fullName}" /></a>
               </span></p>
        </c:forEach>
     <hr/>
     <div align="right">Try <a href="<c:url value="/actions/ppl/ml/AutoResolveMailListProblems">
                        <c:param name="mailListId" value="${actionBean.mailList.id}" />
     </c:url>">automatic</a> or <a href="<c:url value="/actions/ppl/ml/ManualResolveMailListProblems">
                        <c:param name="mailListId" value="${actionBean.mailList.id}" />
     </c:url>">manual</a> problem resolution&nbsp;</div>
</div><br/>
</c:if>
<div align="center"><textarea rows="8" style="width: 80%;" readonly >
<c:forEach items="${actionBean.mailListContent}" var="p2ml" varStatus="status"><c:out
    value="${p2ml.person.fullName}" /> &lt;<c:out value="${p2ml.attributeValue}"/>&gt;<c:if
        test="${not status.last}">,&nbsp;</c:if></c:forEach>
</textarea></div>
<br/>
<h2><span>Members</span> view</h2><br/>
<c:forEach items="${actionBean.mailListContent}" var="p2ml">
    <p><img src="<c:url value="/images/person.png"/>"/><span>
            <a href="<c:url value="/actions/ppl/ml/person/LeaveMailList">
                    <c:param name="personId" value="${p2ml.person.id}" />
                    <c:param name="mailListId" value="${actionBean.mailList.id}" />
                </c:url>"><img src="<c:url value="/images/trash.png"/>"/></a>
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${p2ml.person.id}" />
                </c:url>"><c:out value="${p2ml.person.fullName}" /></a>
            &nbsp;(<a href="mailto:<c:out value="${p2ml.attributeValue}"/>"><c:out value="${p2ml.attributeValue}"/></a>)
       </span></p>
</c:forEach>

<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>