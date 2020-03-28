package io.github.learnjakartaee.controller.main.people;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import io.github.learnjakartaee.config.ActionServlet;

/**
 * XDoclet documentation: http://xdoclet.sourceforge.net/xdoclet/tags/apache-tags.html
 * 
 * @struts.action path="/people" parameter="method" validate="false"
 *                input="/people.do" name="peopleForm"
 *
 * @struts.action path="/manage-people" parameter="method" validate="true"
 *                input="/people.do" name="peopleForm"
 *
 *                Input could go straight to the JSP:
 *                "/WEB-INF/jsp/view/people.jsp"
 * 
 * @struts.action-forward name="view-success" path="/WEB-INF/jsp/view/main/people.jsp"
 *
 * @struts.action-forward name="add-success" path="/people.do" redirect="true"
 *
 * @struts.action-forward name="reset-success" path="/people.do" redirect="true"
 */
public class PeopleAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PeopleForm peopleForm = (PeopleForm) form;
		peopleForm.setPeople(ActionServlet.getPeopleService().getPeople());
		return mapping.findForward("view-success");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PeopleForm peopleForm = (PeopleForm) form;
		boolean isValid = peopleForm.isValid(mapping, request);
		if (isValid) {
			try {
				ActionServlet.getPeopleService().addPerson(peopleForm.getFirstName(), peopleForm.getLastName());
				return mapping.findForward("add-success");
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return view(mapping, peopleForm, request, response);
	}

	public ActionForward reset(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("reset-success");
	}
}
