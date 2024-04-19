<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<layout:layout>
	<jsp:attribute name="mainNavBarSelected">true</jsp:attribute>
	<jsp:attribute name="disableSideMenu">true</jsp:attribute>
	<jsp:body>
		<h2>Page Not Found</h2>
		<a href="<c:url value="/"/>">Home Page</a>
	</jsp:body>
</layout:layout>