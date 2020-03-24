<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World Jakarta EE</title>
</head>
<body>
	<h1>Hello Jakarta JSP and Servlet</h1>

	<c:if test="${requestScope.greeting != null}">
		<c:out value="${requestScope.greeting}" />
		<br>
		<br>
	</c:if>
	<form action="<c:url value="/hello"/>" method="POST">
		What is your name? <input type="text" name="name" />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>