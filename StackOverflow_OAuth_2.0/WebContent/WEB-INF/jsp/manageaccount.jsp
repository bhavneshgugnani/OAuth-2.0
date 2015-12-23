<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account</title>
</head>
<body>
</br>
<h1>Manage Account</h1>
</br>
</br>
</br>
<c:choose>
	<c:when test="${linked == true}">
		This account is already linked with your google accounts.
	</c:when>
	<c:otherwise>
		<h2>Link account with your <a href="/stackoverflow/oauth/redirect">Google</a> Account.</h2>
	</c:otherwise>
</c:choose>
</body>
</html>