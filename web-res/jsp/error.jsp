<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/noPanesHdr.jsp" %>


<h2>Error!</h2>

<p><span>The error had occured. Possible causes:</span></p>
<ul>
    <li>&nbsp;&nbsp;&bull;&nbsp;Serious internal fault</li>
    <li>&nbsp;&nbsp;&bull;&nbsp;Broken database data</li>
    <li>&nbsp;&nbsp;&bull;&nbsp;Form resubmission</li>
    <li>&nbsp;&nbsp;&bull;&nbsp;Attempt to operate with expired data (already deleted objects,...)</li>
</ul>
<hr/>
<p>Contact your vendor please. More detailed information is listed below:</p>
<div align="center" id="errorDiv">
    <h4>Error stack trace:</h4>
    <textarea wrap="off" readonly style="width: 95%; border: dotted 1px;" rows="20"><c:out value="${exception}"/></textarea>
</div>

<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>