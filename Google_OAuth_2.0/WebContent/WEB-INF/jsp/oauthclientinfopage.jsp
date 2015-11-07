<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Info</title>
</head>
<body>
	<h1>O Auth 2.0 Client Information</h1>
	<a href="/google/client/logout">Logout</a>
	<br />
	<br />
	<br /> Client Name : ${clientname}
	<br />
	<br /> Client Username : ${clientusername}
	<br />
	<br /> Client Email : ${clientemail}
	<br />
	<br /> Client Address : ${clientaddress}
	<br />
	<br /> Client Unique ID : ${clientid}
	<br />
	<br /> Unique Redirect Login Url for O Auth 2.0 authentication :
	${clientoauthurl}
	<br />
</body>
</html>