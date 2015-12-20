<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth Account</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" language="javascript">
function removeClient(clientId){
	$.ajax({
		  type: "POST",
		  url: "/google/user/oauth/remove/"+clientId,
		  success: refresh,
		  error: refresh
		});
}

function refresh(){
	location.href = "/google/user/oauth";
}
</script>
</head>
<body>
<div style="height: 10%; width: 100%">
		<div style="padding: 0px 25px 0px 0px; vertical-align: middle;">
			<c:choose>
				<c:when test="${authenticated == true}">
					<label title="name">${username}</label>
					<a href="/google/user/logout"
						style="padding: 0% 0% 0% 90%;"><label title="signout">Sign Out</label></a>
					<a href="/google/user/oauth"
						style="padding: 0% 0% 0% 85%;"><label title="oauth">Manage OAuth Accounts</label></a>
					<a href="/google/" style="padding: 0% 0% 0% 90%;"><label title="home">Home</label></a>
				</c:when>
				<c:otherwise>
					<a href="/google/user/username" style="padding: 0% 0% 0% 85%;"><label
						title="signin">Sign In</label></a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
<br/>
<h1>OAuth 2.0 User Account</h1>
<br/>
<br/>
<br/>
<h3>Below is the list of linked OAuth linked clients linked to your account. Actions for each client can be completed from buttons in front of the client name.</h3>
<br/>
<br/>
<c:choose>
	<c:when test="${oauthclients != null}">
		<c:forEach items="${oauthclients}" var="client">
			${client.clientName}    <button id="${client.clientId}" name="${client.clientId}" onclick="removeClient(this.id)">Remove</button>
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