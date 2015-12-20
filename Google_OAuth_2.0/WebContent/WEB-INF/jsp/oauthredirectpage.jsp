<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OAuth</title>
<script type="text/javascript" language="javascript">
function redirect(){
	setTimeout(function(){
		location.href = "${redirectUrl}";
    }, 3000);
} 
</script>
</head>
<body onload="redirect()">
<br/>
<h1>OAuth 2.0 Access</h1>
<br/>
<br/>
<br/>
<br/>
<c:if test="${message != null}">
	<h2>${message}</h2>
</c:if>
</body>

</html>