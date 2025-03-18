package io.github.learnjakartaee.controller.auth;

import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import io.github.learnjakartaee.config.ActionServlet;

/**
 * @struts.action path="/login" parameter="method" validate="false"
 * 
 * @struts.action-forward name="login-required" path="/WEB-INF/views/auth/login.jsp" validate="false"
 * 
 * @struts.action path="/login" parameter="method" validate="true"
 *                input="/WEB-INF/views/auth/login.jsp" name="loginForm"
 *                
 * @struts.action-forward name="login-success" path="/index.jsp" redirect="true"
 * 
 * @struts.action-forward name="login-failed" path="/WEB-INF/views/auth/login.jsp" redirect="false"
 */
public class LoginAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("login-required");
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginForm loginForm = (LoginForm) form;
		
		Credential credential = new UsernamePasswordCredential(loginForm.getUsername(), loginForm.getPassword());
		AuthenticationStatus status = ActionServlet.getSecurityContext()
				.authenticate(
						request, response,
						AuthenticationParameters.withParams().credential(credential));
		String forward = "login-failed";
		switch (status) {
		case SEND_CONTINUE:
			//facesContext.responseComplete();
			// FIXME: What do we do here?
			break;
		case SEND_FAILURE:
			//facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username and password", null));
			break;
		case SUCCESS:
			forward = "login-success";
			break;
		case NOT_DONE:
		}

		return mapping.findForward(forward);
	}
}
