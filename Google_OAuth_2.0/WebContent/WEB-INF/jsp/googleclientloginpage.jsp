<%@ page import="org.bhavnesh.google.oauth.security.Constants" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h1>OAuth 2.0</h1>
	<br />
	<br />
	<br />
	<h2>Client Login</h2>
	<br />
	<br />
	<c:if test="${Constants.DISPLAY_MESSAGE != null}">
		<h2 style="color: red">${Constants.DISPLAY_MESSAGE}</h2>
	</c:if>
	<form:form action="/google/client/login" method="post"
		modelAttribute="clientloginform">
Username : <input type="text" id="username" name="username" />
		<br />
		<br />
		<br />
Password : <input type="password" id="password" name="password" />
		<br />
		<br />
		<br />
		<button type="submit" value="login">Log In</button>
	</form:form>
	<br/>
	<br/>
	<a href="/google/client/registration">Register</a> for OAuth 2.0 Client Account
</body>
</html>