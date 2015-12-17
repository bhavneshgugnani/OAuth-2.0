<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Authorization</title>
<script type="text/javascript" language="javascript">
function requestToken(){
	location.href = "/stackoverflow/oauth/requestoauthtoken";
} 
</script>
</head>
<body onload="requestToken()">
<br/>
<br/>
<c:if test="${message != null}">
	<h2 style="color: red">${message}</h2>
</c:if>
</body>
</html>