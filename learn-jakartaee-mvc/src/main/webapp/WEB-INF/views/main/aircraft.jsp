<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<layout:layout>
	<jsp:attribute name="mainNavBarSelected">true</jsp:attribute>
	<jsp:attribute name="sidemenu">
      <jsp:include page="/WEB-INF/views/main/sidemenu.jsp" />
    </jsp:attribute>
	<jsp:body>
	<h2>Aircraft Listing</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<td>Designation</td>
				<td>Name</td>
				<td>Nick Name</td>
				<td>Manufacturer</td>
				<td>Produced</td>
				<td>First Flight</td>
				<td>Actions</td>
			</tr>
		</thead>
		<tbody>
    	</tbody>
	</table>
	</jsp:body>
</layout:layout>