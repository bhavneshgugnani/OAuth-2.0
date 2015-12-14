<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<h1>OAuth 2.0 User Login</h1>
<br/>
<br/>
<h2>Please enter your Google account credentials</h2>
<form:form action="/google/user/oauth/${clientId}/userlogin" method="post" modelAttribute="oauthuserloginform">
Email : <input type="text" id="email" name="email"/>
<br/>
<br/>
Password : <input type="password" id="password" name="password"/>
<br/>
<br/>
<input type="hidden" id="responseUrl" name="responseUrl" value="${responseUrl}"/>
<button type="submit" id="submit" name="submit">Login</button>
</form:form>
</body>
</html>