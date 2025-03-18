package io.github.learnjakartaee.controller.auth;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * Login entry form.
 *
 * Property names are defined in ApplicationResources.properties
 *
 * @struts.form name="loginForm"
 */
public class LoginForm extends ValidatorForm {

	private static final long serialVersionUID = 2592322101784243606L;

	private String username;

	private String password;

	/**
	 * Sets the username attribute of the loginForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="3"
	 * @struts.validator-var name="maxlength" value="25"
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * Sets the password attribute of the loginForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="8"
	 * @struts.validator-var name="maxlength" value="50"
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean isValid(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = this.validate(mapping, request);
		return errors == null || errors.isEmpty();
	}
}
