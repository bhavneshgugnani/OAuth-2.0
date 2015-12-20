<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth Account</title>
<script type="text/javascript" language="javascript">
function removeClient(clientId){
	
}
</script>
</head>
<body>
<br/>
<h1>OAuth 2.0 User Account</h1>
<br/>
<br/>
<br/>
<h2>Below is the list of linked OAuth linked clients linked to your account. Actions for each client can be completed from buttons in front of the client name.</h2>
<br/>
<br/>
<c:choose>
	<c:when test="${oauthclients != null}">
		<c:forEach items="${oauthclients}" var="client">
			${client.clientName}    <button id="${client.clientId}" name="${client.clientId}" value="Remove" onclick="removeClient(this.id)"></button>
			<br/>
			<br/>
		</c:forEach>
	</c:when>
	<c:otherwise>
		No OAuth clients linked to your account.
	</c:otherwise>
</c:choose>

</body>
</html>