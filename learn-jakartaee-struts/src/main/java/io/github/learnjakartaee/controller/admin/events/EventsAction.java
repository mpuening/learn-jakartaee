package io.github.learnjakartaee.controller.admin.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @struts.action path="/events" parameter="method" validate="false"
 * 
 * @struts.action-forward name="success" path="/WEB-INF/jsp/view/admin/events.jsp"
 */
public class EventsAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("success");
	}
}
