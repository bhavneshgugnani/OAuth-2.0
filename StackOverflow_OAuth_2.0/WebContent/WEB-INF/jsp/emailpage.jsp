<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Emails</title>
</head>
<body>
<br>
<br>
	<div style="width: 100%; height: 15%;">
			<c:choose>
				<c:when test="${authenticated == true}">
					<a style="padding: 0% 0% 0% 90%;" href="/stackoverflow/signout"><label title="signout">Sign Out</label></a>
					<a style="padding: 0% 0% 0% 85%;" href="/stackoverflow/user/account"><label title="manage">Manage Account</label></a>
					<c:if test="${linked == true}">
						<a style="padding: 0% 0% 0% 85%;" href="/stackoverflow/user/email"><label title="linked">Email from Google</label></a>
					</c:if>
				</c:when>
				<c:otherwise>
					<a style="padding: 0% 0% 0% 90%;" href="/stackoverflow/signup"><label title="signup">Sign Up</label></a>
					<a style="padding: 0% 0% 0% 90%;" href="/stackoverflow/signin"><label title="signin">Sign In</label></a>
				</c:otherwise>
			</c:choose>
	</div>
	<div style="width: 100%; height: 85%;">
		<div style="align: left; padding: 0px 0px 0px 150px; vertical-align: top;">
			<h1>EMails from Google</h1>
		</div>
		<c:if test="${message != null}">
			<h2 style="color: red">${message}</h2>
		</c:if>
	</div>
</body>
</html>