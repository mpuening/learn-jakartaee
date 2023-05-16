<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="main-layout">
	<tiles:put name="body" type="string">
		<html:form action="/update-aircraft" styleClass="needs-validation"
			focus="designation">
			<h2>Edit Aircraft</h2>
			<div class="container-fluid">
				<logic:messagesPresent message="true">
					<html:messages id="msg" message="true">
						<div class="alert alert-danger">
							<bean:write name="msg" />
						</div>
					</html:messages>
					<br>
				</logic:messagesPresent>
				<logic:messagesPresent>
					<p>
						<i><b>Please fix the following errors:</b> </i> <br />
						<html:messages id="errors">
							<div class="alert alert-danger">
								<bean:write name="errors" />
							</div>
						</html:messages>
					</p>
				</logic:messagesPresent>
				<div class="form-group row py-sm-3 mb-0">
					<label for="designation" class="col-sm-2 col-form-label">Designation</label>
					<html:text property="designation" />
					<html:messages id="err_name" property="designation">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="name" class="col-sm-2 col-form-label">Name</label>
					<html:text property="name" />
					<html:messages id="err_name" property="name">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="nickname" class="col-sm-2 col-form-label">Nick Name</label>
					<html:text property="nickname" />
					<html:messages id="err_name" property="nickname">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="manufacturer" class="col-sm-2 col-form-label">Manufacturer</label>
					<html:text property="manufacturer" />
					<html:messages id="err_name" property="manufacturer">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="produced" class="col-sm-2 col-form-label">Produced</label>
					<html:text property="produced" />
					<html:messages id="err_name" property="produced">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="firstFlight" class="col-sm-2 col-form-label">First Flight</label>
					<html:text property="firstFlight" />
					<html:messages id="err_name" property="firstFlight">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<html:hidden property="id" />
					<button name="method" type="submit" value="update"
						class="btn btn-primary">Update</button>
					&nbsp;
					<button name="method" type="submit" value="updateexisting"
						class="btn btn-secondary">Reset</button>
				</div>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>