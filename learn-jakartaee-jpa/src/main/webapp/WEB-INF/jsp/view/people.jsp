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
	<h1>Hello Jakarta JPA</h1>

	<form action="<c:url value="/people"/>" method="POST">
		Add new person? Name: <input type="text" name="name" /> <input
			type="submit" value="Submit" />
	</form>
	<br>
	<br>
	<c:if
		test="${requestScope.people == null || requestScope.people.size() == 0}">
		There are currently no people in the database.
	</c:if>
	<c:if
		test="${requestScope.people != null && requestScope.people.size() > 0}">
		<br>
		<br>
		<table border="1" cellpadding="5px">
			<thead>
				<tr>
					<td>ID</td>
					<td>NAME</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="person" items="${requestScope.people}">
					<tr>
						<td><c:out value="${person.id}"/></td>
						<td><c:out value="${person.name}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

</body>
</html>