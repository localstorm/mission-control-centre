<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<c:if test="${(not empty pclipboard) and (not empty pclipboard.persons)}">
<div id="clipboard">
        <c:forEach items="${pclipboard.persons}" var="person" >
            &nbsp;<a href="<c:url value="/actions/ppl/group/person/UnclipPerson" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="personId" value="${person.id}" />
            </c:url>"><img src="<c:url value="/images/revoke.png"/>" alt="Remove"/></a> 
            <a href="<c:url value="/actions/ppl/group/person/ViewPerson">
                <c:param name="personId" value="${person.id}" />
            </c:url>"><c:out value="${person.fullName}"/></a><br/>
        </c:forEach>
        <div align="right">
            <a href="<c:url value="/actions/ppl/nil/GenerateMailList" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
            </c:url>">Generate mail list</a>&nbsp;
        </div>
</div>
<br/>
</c:if>
