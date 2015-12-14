<%@ page import="org.bhavnesh.google.oauth.security.Constants" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
<c:set var="message" value="${Constants.DISPLAY_MESSAGE}" scope="page"/>
	<div style="height: 150px; width: 100%">
		<h1>
			<label title="googlergistration">Google Registration Form</label>
		</h1>
		<h2>
			<label title="info">Please fill the relevant details below.</label>
		</h2>
	</div>
	<c:if test="${message != null}">
		<h2 style="color: red">${message}</h2>
	</c:if>
	<div style="">
		<form:form action="/google/client/registration" method="post"
			modelAttribute="companyregistrationform">
			<table>
				<tr>
					<td><label title="name">Organization Name : </label></td>
					<td><input type="text" id="clientName" name="clientName">
					</td>
				</tr>
				<br />
				<br />
				<tr>
					<td><label title="address">Organization Address : </label></td>
					<td><input type="text" id="clientAddress" name="clientAddress">
					</td>
				</tr>
				<br />
				<br />
				<tr>
					<td><label title="address">Organization Email Address
							: </label></td>
					<td><input type="text" id="clientEmail" name="clientEmail">
					</td>
				</tr>
				<br />
				<br />
				<tr>
					<td><label title="address">Organization Username : </label></td>
					<td><input type="text" id="clientUsername"
						name="clientUsername"></td>
				</tr>
				<br />
				<br />
				<tr>
					<td><label title="address">Organization Password : </label></td>
					<td><input type="password" id="clientPassword"
						name="clientPassword"></td>
				</tr>
				<br />
				<br />
				<tr>
					<td><label title="userinfoaccess">Select the
							information of user you want to access : </label></td>
					<td></td>
				</tr>
				<tr>
					<button type="submit" value="register">Register</button>
				</tr>
			</table>

		</form:form>
	</div>
</body>
</html>