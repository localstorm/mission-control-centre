<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>Mail Lists</span> list</h2>
    <br/>
    <br/>
	<table width="100%">
    <c:if test="${not empty mailLists}">
        <tr>
            <th colspan="2">Operative</th>
        </tr>
        <c:forEach items="${mailLists}" var="mailList">
            <tr>
                <td width="95%">
                    <p><img src="<c:url value="/images/arrow.gif" />"/>
                                <span><a href="<c:url value="/actions/ppl/ml/ViewMailList">
                                                    <c:param name="mailListId" value="${mailList.id}" />
                                               </c:url>"><c:out value="${mailList.name}"/></a></span>
                </td>
                <td width="5%"> <a href="<c:url value="/actions/ppl/ml/ToggleStateMailList">
                                                    <c:param name="mailListId" value="${mailList.id}" />
                                               </c:url>" title="Archive"><img src="<c:url value="/images/trash.png"/>" /></a></p></td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${not empty actionBean.archiveMailLists}">
        <tr >
                <th colspan="2">Archived</th>
        </tr>
        <c:forEach items="${actionBean.archiveMailLists}" var="mailList" >
        <tr>
                <td width="95%">
                        <p><img src="<c:url value="/images/arrow.gif"/>"/> <span><a href="<c:url
                                    value="/actions/ppl/ml/ViewMailList">
                                        <c:param name="mailListId" value="${mailList.id}" />
                                    </c:url>"><c:out value="${mailList.name}"/></a></span>
                </td>
                <td width="5%"><nobr><a href="<c:url value="/actions/ppl/ml/ToggleStateMailList">
                                            <c:param name="mailListId" value="${mailList.id}" />
                                       </c:url>" title="Unarchive"><img src="<c:url value="/images/deleted.png"/>" /></a>
                                       <a href="<c:url value="/actions/ppl/ml/EraseMailList">
                                            <c:param name="mailListId" value="${mailList.id}" />
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