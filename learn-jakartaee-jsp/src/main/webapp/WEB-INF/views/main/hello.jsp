<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<layout:layout>
	<jsp:attribute name="mainNavBarSelected">true</jsp:attribute>
    <jsp:attribute name="sidemenu">
      <jsp:include page="/WEB-INF/views/main/sidemenu.jsp"/>
    </jsp:attribute>
	<jsp:body>
    <h1 id="banner">Welcome, what is your name?</h1>
	<c:if test="${requestScope.greeting != null}">
		<c:out value="${requestScope.greeting}" />
		<br>
		<br>
	</c:if>
	<form action="<c:url value="/hello"/>" method="POST">
		What is your name? <input type="text" name="name" /> <input
				type="submit" value="Submit" />
	</form>
	</jsp:body>
</layout:layout>