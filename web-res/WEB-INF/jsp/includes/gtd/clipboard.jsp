<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<c:if test="${(not empty clipboard) and 
            ((not empty currContext and (not empty clipboard.lists)) or
            (not empty currList and (not empty clipboard.tasks)))}">
<div id="clipboard">
    <c:if test="${(not empty currContext) and (empty currList)}">
        <c:forEach items="${clipboard.lists}" var="list" >
            &nbsp;<a href="<c:url value="/actions/gtd/ctx/list/ResolveList" >
                <c:param name="listId" value="${list.id}" />
                <c:param name="action" value="PASTE" />
            </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a>
            <a href="<c:url value="/actions/gtd/ctx/list/ViewList">
                <c:param name="listId" value="${list.id}" />
            </c:url>"><c:out value="${list.name}"/></a><br/>
        </c:forEach>
        <c:if test="${fn:length(clipboard.lists)>1}">
            <div align="right">
                <a href="<c:url value="/actions/gtd/ctx/BulkPasteList" >
                    <c:param name="contextId" value="${currContext.id}" />
                </c:url>">Paste all</a>&nbsp;
            </div>
        </c:if>
    </c:if>
    <c:if test="${not empty currList}">
        <c:forEach items="${clipboard.tasks}" var="task" >
            &nbsp;<a href="<c:url value="/actions/gtd/ctx/list/task/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="PASTE" />
            </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a>
            <a href="<c:url value="/actions/gtd/ctx/list/task/ViewTask">
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
            </c:url>"><c:out value="${task.summary}"/></a><br/>
        </c:forEach>

        <c:if test="${fn:length(clipboard.tasks)>1}">
            <div align="right">
                <a href="<c:url value="/actions/gtd/ctx/list/BulkPasteTask" >
                    <c:param name="listId" value="${currList.id}"/>
                </c:url>">Paste all</a>&nbsp;
            </div>
        </c:if>
    </c:if>

</div>
<br/>
</c:if>
