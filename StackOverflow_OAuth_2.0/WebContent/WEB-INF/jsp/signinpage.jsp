<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
</head>
<body>
<h1>StackOverflow Sign In!</h1>
<br/>
<br/>
<br/>
<h2>Sign In</h2>
<br/>
<br/>
<br/>
<form:form action="/stackoverflow/user/login" method="post" modelAttribute="userloginform">
Username : <input type="text" id="username" name="username"/>
<br/>
<br/>
Password : <input type="password" id="password" name="password"/>
<br/>
<br/>
<button type="submit" value="login">Log In</button>
</form:form>
<br/>
<br/>
Sign Up using your <a href="/stackoverflow/oauth/redirect">Google Account!</a>
</body>
</html>