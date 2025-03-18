package io.github.learnjakartaee.controller.auth;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @struts.action path="/logout" parameter="method" validate="false"
 *                input="/WEB-INF/views/auth/logged-out.jsp" name="loginForm"
 * 
 * @struts.action-forward name="success" path="/WEB-INF/views/auth/logged-out.jsp"

 */
public class LogoutAction extends DispatchAction {

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.logout();
		return mapping.findForward("success");
	}
}
