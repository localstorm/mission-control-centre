<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<div id="bodyThirdPan">
    <h2><span>Reference</span> pane</h2>       
    <p class="more"><a href="<c:url value="/actions/gtd/nil/EditRefObj"/>">EDIT</a></p>
    <table class="refobjs">
        <c:forEach items="${refObjects}" var="ro">
            <c:choose>
                <c:when test="${not empty actionBean.currentContext}">
                    <c:if test="${ro.context.id eq actionBean.currentContext.id}">
                    <tr>
                        <td width="18px" valign="top"><img src="<c:url value="/images/refobj.png"/>"/></td>
                        <td><a href="<c:url value="/actions/gtd/ctx/obj/ViewRefObj">
                                        <c:param name="objectId" value="${ro.id}"/>
                                    </c:url>"><c:out value="${ro.name}" /></a></td>
                    </tr>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty affectedContexts}">
                            <c:if test="${affectedContexts[ro.context.id]}">
                            <tr>
                                <td width="18px" valign="top"><img src="<c:url value="/images/refobj.png"/>"/></td>
                                <td><a href="<c:url value="/actions/gtd/ctx/obj/ViewRefObj">
                                                <c:param name="objectId" value="${ro.id}"/>
                                            </c:url>"><c:out value="${ro.name}" /></a></td>
                            </tr>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td width="18px" valign="top"><img src="<c:url value="/images/refobj.png"/>"/></td>
                                <td><a href="<c:url value="/actions/gtd/ctx/obj/ViewRefObj">
                                                <c:param name="objectId" value="${ro.id}"/>
                                            </c:url>"><c:out value="${ro.name}" /></a></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>                
</div>
