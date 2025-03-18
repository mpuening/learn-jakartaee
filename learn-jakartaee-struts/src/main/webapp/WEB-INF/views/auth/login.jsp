<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="base-layout">
	<tiles:put name="body" type="string">
		<div class="row">
			<div class="col-sm-6 offset-sm-3 text-center">
				<h1 class="display-4">Welcome</h1>
				<div>
					<html:form action="/login" styleClass="text-center border border-light p-5" focus="username">
						<p id="loginMessage" class="h4 mb-4">Please sign in...</p>
						FIXME PUT param messages here..
						<br>
						<label for="username" class="sr-only">Username</label>
						<html:text styleId="username" property="username" styleClass="form-control mb-2"/>
						<html:messages id="err_name" property="username">
							<div class="is-invalid text-danger">
								&nbsp;
								<bean:write name="err_name" />
							</div>
						</html:messages>
						<label for="password" class="sr-only">Password</label>
						<html:password styleId="password" property="password" styleClass="form-control mb-2"/>
						<html:messages id="err_name" property="password">
							<div class="is-invalid text-danger">
								&nbsp;
								<bean:write name="err_name" />
							</div>
						</html:messages>
						<button name="method" type="submit" value="login"
							class="btn btn-info btn-block my-4">Sign In</button>
					</html:form>
				</div>
			</div>
		</div>
	</tiles:put>
</tiles:insert>