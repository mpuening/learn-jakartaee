<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<layout:layout>
	<jsp:attribute name="mainNavBarSelected">true</jsp:attribute>
	<jsp:attribute name="sidemenu">
      <jsp:include page="/WEB-INF/views/main/sidemenu.jsp" />
    </jsp:attribute>
	<jsp:body>
		<h2>Help</h2>
		You could place help information here.
	</jsp:body>
</layout:layout>