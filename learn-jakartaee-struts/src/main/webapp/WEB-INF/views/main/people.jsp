<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="main-layout">
	<tiles:put name="body" type="string">
		<html:form action="/people">
			<h2>People Listing</h2>
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
			<logic:notEmpty name="peopleForm" property="people">
				<table class="table table-striped">
					<thead>
						<tr>
							<td>Id</td>
							<td>First Name</td>
							<td>Last Name</td>
							<td>Actions</td>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="person" name="peopleForm" property="people">
							<tr>
								<td><c:out value="${person.id}" /></td>
								<td><c:out value="${person.firstName}" /></td>
								<td><c:out value="${person.lastName}" /></td>
								<td><a
									href="<c:url value="/people.do">
										<c:param name="method" value="updateexisting" />
										<c:param name="id" value="${person.id}" />
									</c:url>"
									class="btn btn-info">Edit</a> <a
									href="<c:url value="/people.do">
										<c:param name="method" value="delete" />
										<c:param name="id" value="${person.id}" />
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