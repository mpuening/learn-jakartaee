<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<layout:layout>
	<jsp:attribute name="adminNavBarSelected">true</jsp:attribute>
    <jsp:attribute name="sidemenu">
      <jsp:include page="/WEB-INF/views/admin/sidemenu.jsp"/>
    </jsp:attribute>
	<jsp:body>
		<h2>Support</h2>
		You could place support information here.
	</jsp:body>
</layout:layout>