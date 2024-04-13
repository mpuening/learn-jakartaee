<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
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
    		<c:forEach items="${aircraftList}" var="aircraft">
    			<tr>
    				<td><c:out value="${aircraft.designation}" /></td>
    				<td><c:out value="${aircraft.name}" /></td>
    				<td><c:out value="${aircraft.nickname}" /></td>
    				<td><c:out value="${aircraft.manufacturer}" /></td>
    				<td><c:out value="${aircraft.produced}" /></td>
    				<td><c:out value="${aircraft.firstFlight}" /></td>
    				<td>None</td>
				</tr>
			</c:forEach>
    	</tbody>
	</table>
	</jsp:body>
</layout:layout>