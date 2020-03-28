package io.github.learnjakartaee.controller.main.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @struts.action path="/help" parameter="method" validate="false"
 * 
 * @struts.action-forward name="success" path="/WEB-INF/jsp/view/main/help.jsp"
 */
public class HelpAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("success");
	}
}
