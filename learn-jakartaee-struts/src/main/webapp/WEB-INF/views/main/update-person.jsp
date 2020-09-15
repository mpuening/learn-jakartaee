<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="main-layout">
	<tiles:put name="body" type="string">
		<html:form action="/update-people" styleClass="needs-validation"
			focus="firstName">
			<h2>Edit Person</h2>
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
					<label for="firstName">First Name &nbsp;</label>
					<html:text property="firstName" />
					<html:messages id="err_name" property="firstName">
						<div class="is-invalid text-danger">
							&nbsp;
							<bean:write name="err_name" />
						</div>
					</html:messages>
				</div>
				<div class="form-group row py-sm-3 mb-0">
					<label for="lastName">Last Name &nbsp;</label>
					<html:text property="lastName" />
					<html:messages id="err_name" property="lastName">
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