<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<c:if test="${(not empty clipboard) and 
            (not empty currRefObject and (not empty clipboard.notes or (not empty clipboard.files)))}">
<div id="clipboard">
    <c:if test="${not empty clipboard.notes}">
    &nbsp;<a href="<c:url value="/actions/gtd/ctx/obj/note/ResolveRefObjNote" >
            <c:param name="noteId"   value="-1" />
            <c:param name="objectId"   value="${currRefObject.id}" />
            <c:param name="action" value="PASTE_NOTES" />
        </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a><c:out value="Notes and URLs (${fn:length(clipboard.notes)})"/><br/>
    </c:if>
    <c:if test="${not empty clipboard.files}">
    &nbsp;<a href="<c:url value="/actions/gtd/ctx/obj/file/ResolveRefObjFile" >
            <c:param name="fileId"   value="-1" />
            <c:param name="objectId"   value="${currRefObject.id}" />
            <c:param name="action" value="PASTE_FILES" />
        </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a><c:out value="Files (${fn:length(clipboard.files)})"/>
    </c:if>
</div>
<br/>
</c:if>
