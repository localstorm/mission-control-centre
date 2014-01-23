<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>
		
<%@ include file="/WEB-INF/jsp/includes/gtd/roClipboard.jsp" %>
<h2><span>OBJECT</span> details</h2>
    
    <div align="right" width="80%">Attach&nbsp;<a href="#" onclick="show('attachTextDiv', 'text-id'); hide('attachFileDiv'); hide('attachURLDiv'); hide('updateDiv'); return false">Note</a> |
    <a href="#" onclick="show('attachURLDiv', 'text-id'); hide('attachFileDiv'); hide('attachTextDiv'); hide('updateDiv'); return false">URL</a> |
    <a href="#" onclick="show('attachFileDiv', 'desc-id'); hide('attachTextDiv'); hide('attachURLDiv'); hide('updateDiv'); return false">File</a>
        (<a href="#" onclick="show('updateDiv', 'new-name-id'); hide('attachTextDiv'); hide('attachURLDiv'); hide('attachFileDiv');"><c:out value="${actionBean.objectResult.name}"/></a>)</div>
    
    <div align="center">

    <div id="updateDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors
                             and not empty updateForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/ctx/obj/UpdateRefObj" >
        <stripes:hidden name="objectId" value="${actionBean.objectResult.id}"/>
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Context: </td>
                <td><stripes:select name="contextId" style="width: 100%;" value="${actionBean.currentContext.id}">
                        <c:forEach items="${contexts}" var="ctx">
                            <stripes:option value="${ctx.id}" label="${ctx.name}"/>
                        </c:forEach>
                    </stripes:select></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="new-name-id" style="width: 100%;" value="${actionBean.objectResult.name}" /></td>
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
                    <stripes:submit name="submit" value="Update" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('updateDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>

    <div id="attachTextDiv" width="75%" style="display:
            <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors 
                             and empty urlForm and empty fileForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
            </c:choose>;">
        <stripes:form name="textForm" action="/actions/gtd/ctx/obj/AttachRefObj" > 
            <stripes:hidden name="type" value="TEXT" />
            <stripes:hidden name="objectId" value="${actionBean.objectResult.id}" />
            <stripes:hidden name="description" value="--"/>
            <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" width="100%">
            <tr>
                <td align="center" colspan="4">Text:</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2">
                    <stripes:textarea rows="10" name="text" id="text-id" style="width: 100%;" />
                </td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;" />&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('attachTextDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    
    <div id="attachURLDiv" width="80%" style="display:
            <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors 
                             and not empty urlForm and empty fileForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
            </c:choose>;">
        <stripes:form name="urlForm" action="/actions/gtd/ctx/obj/AttachRefObj" > 
            <stripes:hidden name="type" value="URL" />
            <stripes:hidden name="objectId" value="${actionBean.objectResult.id}" />
            <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Description: </td>
                <td><stripes:text name="description" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>URL: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;" />&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('attachURLDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="attachFileDiv" width="80%" style="display:
            <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors
                             and not empty fileForm and empty urlForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
            </c:choose>;">
        <stripes:form action="/actions/ctx/obj/UploadRefObj" >
            <stripes:hidden name="objectId" value="${actionBean.objectResult.id}" />
            <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Description: </td>
                <td><stripes:text name="description" id="desc-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>File: </td>
                <td><stripes:file name="file" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('attachFileDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    <c:forEach var="note" items="${actionBean.objectTextualNotes}">    
        <p><span><a title="Detach" onclick="return confirm('Are you sure?')" href="<c:url value="/actions/gtd/ctx/obj/note/DetachNoteRefObj" >
            <c:param name="objectId" value="${actionBean.objectResult.id}" />
            <c:param name="noteId" value="${note.id}" />
        </c:url>"><img src="<c:url value="/images/trash.png"/>" alt="detach" /></a><a title="Cut" href="<c:url value="/actions/gtd/ctx/obj/note/ResolveRefObjNote" >
            <c:param name="action"     value="CUT_NOTE" />
            <c:param name="objectId"   value="${actionBean.objectResult.id}" />
            <c:param name="noteId" value="${note.id}" />
        </c:url>"><img src="<c:url value="/images/cut.png"/>" alt="detach" /></a>&nbsp;Text: </span>&nbsp;<br/>
            <table width="100%">
                <tr><td align="justify"><c:out escapeXml="false" value="${note.noteHtmlEscaped}" /></td></tr>
            </table>
        </p>
    </c:forEach>
    
    <c:if test="${not empty actionBean.objectUrlNotes}">
        <c:if test="${not empty actionBean.objectTextualNotes}">
            <hr/>
        </c:if>
        <c:forEach var="note" items="${actionBean.objectUrlNotes}">    
            <p><span><a title="Detach" onclick="return confirm('Are you sure?')" href="<c:url value="/actions/gtd/ctx/obj/note/DetachNoteRefObj" >
                <c:param name="objectId" value="${actionBean.objectResult.id}" />
                <c:param name="noteId"   value="${note.id}" />
            </c:url>"><img src="<c:url value="/images/trash.png"/>" alt="detach" /></a><a title="Cut" href="<c:url value="/actions/gtd/ctx/obj/note/ResolveRefObjNote" >
            <c:param name="action"   value="CUT_NOTE" />
            <c:param name="objectId"   value="${actionBean.objectResult.id}" />
            <c:param name="noteId"   value="${note.id}" />
        </c:url>"><img src="<c:url value="/images/cut.png"/>" alt="detach" /></a>&nbsp;Link</span>: <a href="<c:out value="${note.note}" />"><c:out value="${note.description}" /></a></p>
        </c:forEach>
    </c:if>

    <c:if test="${not empty actionBean.objectFiles}">
        <c:if test="${not empty actionBean.objectUrlNotes or not empty actionBean.objectTextualNotes}">
            <hr/>
        </c:if>
        <c:forEach var="file" items="${actionBean.objectFiles}">    
            <p><span><a title="Detach" onclick="return confirm('Are you sure?')" href="<c:url value="/actions/gtd/ctx/obj/file/DetachFileRefObj" >
                <c:param name="objectId" value="${actionBean.objectResult.id}" />
                <c:param name="fileId"   value="${file.id}" />
        </c:url>"><img src="<c:url value="/images/trash.png"/>" alt="detach" /></a><a title="Cut" href="<c:url value="/actions/gtd/ctx/obj/file/ResolveRefObjFile" >
            <c:param name="action"     value="CUT_FILE" />
            <c:param name="objectId"   value="${actionBean.objectResult.id}" />
            <c:param name="fileId"     value="${file.id}" />
        </c:url>"><img src="<c:url value="/images/cut.png"/>" alt="detach" /></a>&nbsp;File</span>: <a href="<c:url value="/actions/gtd/ctx/obj/file/DownloadFile">
                <c:param name="fileId" value="${file.id}" />        
        </c:url>"><c:out value="${file.name}"/></a> (<c:out value="${file.description}" />)</p>
        </c:forEach>
    </c:if>
    
<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>