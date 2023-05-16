<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="main-layout">
	<tiles:put name="body" type="string">
		<html:form action="/aircraft">
			<h2>Aircraft Listing</h2>
			<logic:messagesPresent message="true">
				<html:messages id="msg" message="true">
					<div class="alert alert-danger">
						<bean:write name="msg" />
					</div>
				</html:messages>
				<br>
			</logic:messagesPresent>
			<div class="container-fluid">
				<div class="form-group row py-sm-3 mb-0">
					<button name="method" type="submit" value="addnew"
						class="btn btn-primary">Add New</button>
				</div>
			</div>
			<logic:notEmpty name="aircraftForm" property="aircraft">
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
						<logic:iterate id="aircraft" name="aircraftForm" property="aircraft">
							<tr>
								<td><c:out value="${aircraft.designation}" /></td>
								<td><c:out value="${aircraft.name}" /></td>
								<td><c:out value="${aircraft.nickname}" /></td>
								<td><c:out value="${aircraft.manufacturer}" /></td>
								<td><c:out value="${aircraft.produced}" /></td>
								<td><c:out value="${aircraft.firstFlightAsString}" /></td>
								<td><a
									href="<c:url value="/aircraft.do">
										<c:param name="method" value="updateexisting" />
										<c:param name="id" value="${aircraft.id}" />
									</c:url>"
									class="btn btn-info">Edit</a> <a
									href="<c:url value="/aircraft.do">
										<c:param name="method" value="delete" />
										<c:param name="id" value="${aircraft.id}" />
									</c:url>"
									class="btn btn-danger">Delete</a></td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</logic:notEmpty>
		</html:form>
	</tiles:put>
</tiles:insert>