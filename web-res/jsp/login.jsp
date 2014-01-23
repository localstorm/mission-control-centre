<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/loginhdr.jsp" %>

<br/><br/><br/>
<form action='<c:url value="/auth" />' method="POST" >
<div align="center">
<c:if test="${invalidLoginOrPassword}">
    <table width="30%" bgColor="#F0E68C" >
        <tr>
            <th colspan="2" bgColor="#BDB76B"><font color="white">Access Denied</font></th>
        </tr>
        <tr>
            <th colspan="2" >&nbsp;</th>
        </tr>
        <tr>
            <td align="right"><img alt="Access denied" src='<c:url value="/images/lock.png"/>' style="padding: 4px;"/></td>
            <td>Login or password invalid. Try again please.</td>
        </tr>
        <tr>
            <th colspan="2" >&nbsp;</th>
        </tr>
    </table>
    <br/>
</c:if>
<table width="30%" bgColor="#CCCCEE" >
	<tr>
		<th colspan="2" bgColor="#7777EE"><font color="white">Sign in</font></th>
	</tr>
	<tr>
		<th colspan="2" >&nbsp;</th>
	</tr>
	<tr>
		<td align="right">Login:</td>
		<td><input type="text" style="width: 80%;" name="__login"/></td>
	</tr>
	<tr>
		<td align="right">Password:</td>
		<td><input type="password" style="width: 80%;" name="__pass"/></td>
	</tr>
	<tr>
		<th colspan="2" >&nbsp;</th>
	</tr>
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Enter"/></td>
	</tr>
</table>
</div>
</form>
 

