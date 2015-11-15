<%@ page import="org.bhavnesh.google.oauth.security.Constants"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
</head>
<body>
	<div
		style="height: 40%; width: 100%; align: center; vertical-align: middle;">
		<div style="vertical-align: middle; padding: 0% 0% 0% 50%;">
			<h1>Google</h1>
		</div>
	</div>
	<div style="height: 60%; width: 100%;">
		<div align="center">
			<h2>Hey ${firstname} ${lastname}</h2>
			<br/>
			<h3>${email}</h3>
			<c:choose>
				<c:when test="${Constants.DISPLAY_MESSAGE != null}">
					<h2>${Constants.DISPLAY_MESSAGE}</h2>
				</c:when>
				<c:otherwise>
					<h2>Please enter your password</h2>
				</c:otherwise>
			</c:choose>
			<form:form action="/google/user/login" method="post" modelAttribute="singlevalueform">
			<br /> <input type="password" id="value" name="value" /> <br /> <br />
			<input type="submit" value="Sign In"/>
			</form:form>
			<br /> <br /> <br /> <a href="/google/create"><label
				title="create">Create Account</label></a>
		</div>
	</div>
</body>
</html>