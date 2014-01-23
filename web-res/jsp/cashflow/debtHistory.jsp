<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>DEBT</span> history</h2>
<div align="right">
    Truncate history up to: <a href="<c:url value="/actions/cash/nil/TruncateHistory">
        <c:param name="returnPageToken" value="${returnPageToken}" />
        <c:param name="valueTag" value="DEBT_CHECKPOINT"/>
        <c:param name="keepDays" value="365"/>
    </c:url>">1Y</a>, <a href="<c:url value="/actions/cash/nil/TruncateHistory">
        <c:param name="returnPageToken" value="${returnPageToken}" />
        <c:param name="valueTag" value="DEBT_CHECKPOINT"/>
        <c:param name="keepDays" value="182"/>
    </c:url>">6M</a>, <a href="<c:url value="/actions/cash/nil/TruncateHistory">
        <c:param name="returnPageToken" value="${returnPageToken}" />
        <c:param name="valueTag" value="DEBT_CHECKPOINT"/>
        <c:param name="keepDays" value="90"/>
    </c:url>">3M</a>, <a href="<c:url value="/actions/cash/nil/TruncateHistory">
        <c:param name="returnPageToken" value="${returnPageToken}" />
        <c:param name="valueTag" value="DEBT_CHECKPOINT"/>
        <c:param name="keepDays" value="30"/>
    </c:url>">1M</a>, <a onclick="return confirm('Are you sure?');" href="<c:url value="/actions/cash/nil/TruncateHistory">
        <c:param name="returnPageToken" value="${returnPageToken}" />
        <c:param name="valueTag" value="DEBT_CHECKPOINT"/>
        <c:param name="keepDays" value="-1"/>
    </c:url>">now</a>
</div>
<div align="center">
    <br/>
    <img src="<c:url value="/chart/debtHistory.png"/>"/>
</div>


<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>