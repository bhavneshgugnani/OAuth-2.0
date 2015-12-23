<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google</title>
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
					<a href="/google/user/email"
						style="padding: 0% 0% 0% 90%;"><label title="oauth">GMail</label></a>	
				</c:when>
				<c:otherwise>
					<a href="/google/user/username" style="padding: 0% 0% 0% 85%;"><label
						title="signin">Sign In</label></a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div style="height: 10%; width: 100%">
		<div style="padding: 0px 25px 0px 0px; vertical-align: middle;">
			<a href="/google/client" style="padding: 0% 0% 0% 85%;"><label
						title="oauth_page">OAuth 2.0 Support</label></a>
		</div>
	</div>
	<div style="height: 80%; width: 100%">
		<div style="vertical-align: middle; padding: 0% 0% 0% 40%;">
			<h1>Welcome To Google!</h1>
			<br />
			<div style="padding: 0% 0% 0% 10%;">
				<input id="searchtext" name="searchtext" type="text" /> <br />
				<button id="search" name="search">Search</button>
				<button id="feelinglucky" name="feelinglucky">Feeling Lucky</button>
			</div>
		</div>
	</div>
</body>
</html>