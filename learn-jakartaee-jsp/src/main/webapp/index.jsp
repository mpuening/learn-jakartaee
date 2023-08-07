<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<layout:layout>
	<jsp:attribute name="mainNavBarSelected">true</jsp:attribute>
	<jsp:attribute name="disableSideMenu">true</jsp:attribute>
	<jsp:body>
		 <h2 id="banner">Welcome</h2>
		<a href="<c:url value="/hello"/>">Say Hello</a>
	</jsp:body>
</layout:layout>