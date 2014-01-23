<%@ page pageEncoding="UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<stripes:form action="/actions/gtd/ctx/SetContextFilter" >
    <stripes:hidden name="returnPageToken" value="${param.returnPageToken}" />
    <stripes:select name="contextId" value="${filterCtx}" onchange="submit();">
        <stripes:option value="-1" >[Show all]</stripes:option>
        <c:forEach items="${contexts}" var="ctx" >
            <stripes:option value="${ctx.id}" ><c:out value="${ctx.name}"/></stripes:option>
        </c:forEach>
    </stripes:select>
</stripes:form>