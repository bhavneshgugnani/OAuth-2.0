<%@ page import="org.bhavnesh.google.oauth.security.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth 2.0</title>
<!-- <c:set var="clientId" value="${clientId}"/> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" language="javascript">
function submitPermission(grantPermission){
	$.ajax({
        'url' : '/google/user/oauth/${clientId}/permission?permissionGrant='+grantPermission,
        'type' : 'POST',
        'success' : function(data) {              
            alert();
        },
        'error' : function(request,error)
        {
            alert("Request: "+JSON.stringify(request));
        }
    });
} 

function test(){
	alert("test");
}
</script>
</head>

<body>
<div style="height: 10%; width: 100%">
		<div style="padding: 0px 25px 0px 0px; vertical-align: middle;">
			<c:choose>
				<c:when test="${authenticated == true}">
					<label title="name">${displayname}</label>
					<a href="/google/user/oauth/${clientId}/userlogout"
						style="padding: 0% 0% 0% 90%;"><label title="signin">Sign Out</label></a>
				</c:when>				
			</c:choose>
		</div>
	</div>
<div style="height: 90%; width: 100%">
<br/>
<h1>OAuth 2.0 Permission Granting Page</h1>
<br/>
<br/>
<p>${clientName} has requested to access your google account information. You can allow the requested to access the information and also revoke the permissions form your account settings. Do you want to allow the requester to access your google account information?</p>
<br/>
<br/>
<button id="allow" name="allow" onclick="submitPermission('true')">Allow</button>
<button id="deny" name="deny" onclick="submitPermission('false')">Deny</button>
</div>
</body>
</html>