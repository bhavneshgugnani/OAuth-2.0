<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
</head>
<body>
<h1>User Registration</h1>
<br/>
<br/>
<form:form action="/google/user/register" method="post" modelAttribute="userregistrationform">
First Name : <input type="text" id="firstname" name="firstname"/>
<br/>
<br/>
Last Name : <input type="text" id="lastname" name="lastname"/>
<br/>
<br/>
Age : <input type="text" id="age" name="age"/>
<br/>
<br/>
Gender : <select id="gender" name="gender">
		     <option value="Male">Male</option>
		     <option value="Female">Female</option>
		 </select>
<br/>
<br/>
Address : <input type="text" id="address" name="address"/>
<br/>
<br/>
Phone Number : <input type="text" id="phone" name="phone"/>
<br/>
<br/>
Username : <input type="text" id="username" name="username"/>@gmail.com
<br/>
<br/>
Password : <input type="password" id="password" name="password"/>
<br/>
<br/>
Confirm Password : <input type="password" id="password2" name="password2"/>
<br/>
<br/>
<button type="submit" value="register">Register</button>
</form:form>
</body>
</html>