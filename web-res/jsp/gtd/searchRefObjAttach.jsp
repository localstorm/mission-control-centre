<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>REFERENCE</span> search</h2>
    <div align="right" width="80%"><a href="#" onclick="show('roaDiv', 'text-id'); return false">Search again</a></div>
    <div align="center">

    <div id="roaDiv" width="80%" style="display: <c:choose>
             <c:when test="${not actionBean.found}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/gtd/nil/SubmitRefObjAttachSearch" focus="text">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Text: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" value="${actionBean.text}" /></td>
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
                    <stripes:submit name="submit" value="Search" style="width: 7em;"/>
                    <c:if test="${actionBean.found}">
                        <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('roaDiv'); return false" />
                    </c:if>

                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    <br/>
    <c:if test="${actionBean.found}">
        <c:forEach var="note" items="${actionBean.objectTextualNotes}">
            <p><span><c:out value="${note.refObject.context.name}"/>, <c:out value="${note.refObject.name}"/>, Text</span>:<br/>
                <table width="100%">
                    <tr><td><c:out escapeXml="false" value="${note.noteHtmlEscaped}" /></td></tr>
                </table>
            </p>
        </c:forEach>

        <c:if test="${not empty actionBean.objectUrlNotes}">
            <c:if test="${not empty actionBean.objectTextualNotes}">
                <hr/>
            </c:if>
            <c:forEach var="note" items="${actionBean.objectUrlNotes}">
                <p><span><c:out value="${note.refObject.context.name}"/>, <c:out value="${note.refObject.name}"/>, Link</span>: <a href="<c:out value="${note.note}" />"><c:out value="${note.description}" /></a></p>
            </c:forEach>
        </c:if>

        <c:if test="${not empty actionBean.objectFiles}">
            <c:if test="${not empty actionBean.objectUrlNotes or not empty actionBean.objectTextualNotes}">
                <hr/>
            </c:if>
            <c:forEach var="file" items="${actionBean.objectFiles}">
                <p><span><c:out value="${file.refObject.context.name}"/>, <c:out value="${file.refObject.name}"/>, File</span>: <a href="<c:url value="/actions/gtd/ctx/obj/file/DownloadFile">
                    <c:param name="fileId" value="${file.id}" />
            </c:url>"><c:out value="${file.name}"/></a> (<c:out value="${file.description}" />)</p>
            </c:forEach>
        </c:if>
    </c:if>

<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>