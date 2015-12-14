<%@ page import="org.bhavnesh.google.oauth.security.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google</title>
</head>
<body>
<c:set var="message" value="${Constants.DISPLAY_MESSAGE}" scope="page"/>
<c:set var="responseUrl" value="${Constants.RESPONSE_URL}" scope="page"/>
<br/>
<h1>OAuth 2.0</h1>
<br/>
<br/>
<h3>>${message}</h3>
<br/>
<br/>
<c:if test="${responseUrl != null}">
<a href="${responseUrl}">Back to Originating website</a>
</c:if>
</body>
</html>