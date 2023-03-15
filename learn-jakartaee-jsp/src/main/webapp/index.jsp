<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<layout:layout>
	<jsp:attribute name="header">
      <h1 id="banner">Welcome</h1>
    </jsp:attribute>
	<jsp:body>
		<a href="<c:url value="/hello"/>">Say Hello</a>
	</jsp:body>
</layout:layout>