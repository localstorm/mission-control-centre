<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>TASK</span> details</h2>

<script type="text/javascript">
    function hintz(form)
    {
        show('hintsDiv');
        hide('datesDiv');
        form['mode'].value='HINTS';
        return false;
    }

    function dates(form)
    {
        hide('hintsDiv');
        show('datesDiv');
        form['mode'].value='DATES';
        return false;
    }
</script>
<c:choose>
    <c:when test="${not actionBean.taskResult.hinted}"><c:set var="initialMode" value="DATES"/></c:when>
    <c:otherwise><c:set var="initialMode" value="HINTS"/></c:otherwise>
</c:choose>
<stripes:form action="/actions/gtd/ctx/list/task/UpdateTask" focus="summary" name="taskForm" >
<stripes:errors/>
<stripes:hidden name="mode"   value="${initialMode}" />
<stripes:hidden name="taskId" value="${actionBean.taskResult.id}" />
<stripes:hidden name="returnPageToken" value="${param.returnPageToken}" />
<table width="100%">

<c:if test="${not empty actionBean.taskResult.runtimeNote}"><tr>
    <th colspan="2"><font color="red"><c:out value="${actionBean.taskResult.runtimeNote}" /></font></th>
</tr>
</c:if>
<tr>
    <th colspan="2" align="left">
        <table width="100%">
            <tr align="left">
                <th>Summary:</th>
                <th width="20%" align="right">
                    <stripes:select name="effort" value="${actionBean.taskResult.effort}">
                        <c:forEach items="${actionBean.efforts}" var="effort">
                            <stripes:option value="${effort.effort}"><c:out value="${effort.latinName}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </th>
            </tr>
        </table>
    </th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="4" style="width: 100%;" name="summary" value="${actionBean.taskResult.summary}"/></td>
</tr>
<tr>
    <th colspan="2" align="left">Details:</th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="6" style="width: 100%;" name="details" value="${actionBean.taskResult.details}"/></td>
</tr>
</table>
<center>
<div id="datesDiv" width="100%" style="display: <c:choose>
         <c:when test="${not actionBean.taskResult.hinted}">inline</c:when>
         <c:otherwise>none</c:otherwise>
</c:choose>;">
    <table width="100%" >
        <tr>
            <th colspan="2" align="left">
                Dates <input type="button" onclick="hintz(this.form); return false" value="Hints"/>:
            </th>
        </tr>
        <tr>
            <td>
                <table width="100%">
                    <tr>
                        <td bgcolor="red"><font color="yellow">Red&nbsp;Line:</font></td>
                        <td>
                            <stripes:text readonly="true" name="redline" id="r1" size="10" value="${actionBean.redline}"/><img onclick="showCalendar('r1', '%d.%m.%Y', false, true);" border="0px" src="<c:url value="/images/calendar.png"/>" />&nbsp;<img onclick="document.taskForm.redline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" />
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table width="100%">
                    <tr>
                        <td bgcolor="black"><font color="white">Dead&nbsp;Line:</font></td>
                        <td>
                            <stripes:text readonly="true" name="deadline" id="d1" size="10" value="${actionBean.deadline}"/><img onclick="showCalendar('d1', '%d.%m.%Y', false, true);" border="0px" src="<c:url value="/images/calendar.png"/>" />&nbsp;<img onclick="document.taskForm.deadline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div id="hintsDiv" width="100%" style="display: <c:choose>
         <c:when test="${not actionBean.taskResult.hinted}">none</c:when>
         <c:otherwise>inline</c:otherwise>
</c:choose>;">
    <table width="100%">
        <tr align="left">
            <th colspan="2">
                Hints <input type="button" onclick="dates(this.form); return false" value="Dates"/>:
            </th>
        </tr>
    
        <tr align="left">
            <td width="50%"><stripes:checkbox name="hints" checked="${actionBean.taskResult.hints['YEARLY']}" value="YEARLY" />&nbsp;Hint every year</td>
            <td width="50%"><stripes:checkbox name="hints" checked="${actionBean.taskResult.hints['SUNDAY']}" value="SUNDAY" />&nbsp;Hint every Sunday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['MONTHLY']}" name="hints" value="MONTHLY"/>&nbsp;Hint every month</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['MONDAY']}" name="hints" value="MONDAY" />&nbsp;Hint every Monday</td>
        </tr>
        <tr align="left">
            
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['WEEKLY']}" name="hints" value="WEEKLY"/>&nbsp;Hint every week</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['TUESDAY']}" name="hints" value="TUESDAY"/>&nbsp;Hint every Tuesday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['DAILY']}" name="hints" value="DAILY"/>&nbsp;Hint every day</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['WEDNESDAY']}" name="hints" value="WEDNESDAY"/>&nbsp;Hint every Wednesday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['ANY_WORKDAY']}" name="hints" value="ANY_WORKDAY"/>&nbsp;Hint after the weekend</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['THURSDAY']}" name="hints" value="THURSDAY"/>&nbsp;Hint every Thursday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['ANY_WEEKEND_DAY']}" name="hints" value="ANY_WEEKEND_DAY"/>&nbsp;Hint at the weekend</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['FRIDAY']}" name="hints" value="FRIDAY"/>&nbsp;Hint every Friday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['EVERY_WEEKEND_DAY']}" name="hints" value="EVERY_WEEKEND_DAY"/>&nbsp;Hint at Sun, Sat</td>
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['SATURDAY']}" name="hints" value="SATURDAY"/>&nbsp;Hint every Saturday</td>
        </tr>
        <tr align="left">
            <td><stripes:checkbox checked="${actionBean.taskResult.hints['EVERY_WORKDAY']}" name="hints" value="EVERY_WORKDAY" />&nbsp;Hint at Mon-Fri</td>
            <td>&nbsp;</td>
        </tr>
    </table>
</div>
</center>
<table width="100%">
<tr>
    <td colspan="2" align="center"><stripes:submit name="submit" value="Apply" />&nbsp;<stripes:reset name="reset"/></td>
</tr>
</table>
</stripes:form>



<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>