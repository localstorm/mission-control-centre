<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>INBOX</span></h2>
<br/>
<c:if test="${not empty actionBean.inbox}">
<c:forEach items="${actionBean.inbox}" var="entry">
    <p><a href="<c:url value="/actions/gtd/ibx/EraseInboxEntry">
        <c:param name="entryId" value="${entry.id}"/>
        </c:url>" title="Delete note"><img src="<c:url value="/images/erase.png"/>" /></a> <c:out escapeXml="false" value="${entry.contentHtmlEscaped}"/></p>
    <hr/>
</c:forEach>
</c:if>
 
<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>