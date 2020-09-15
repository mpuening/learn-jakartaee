package io.github.learnjakartaee.controller.main.people;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import io.github.learnjakartaee.config.ActionServlet;
import io.github.learnjakartaee.model.Person;
import io.github.learnjakartaee.service.PeopleException;

/**
 * XDoclet documentation: http://xdoclet.sourceforge.net/xdoclet/tags/apache-tags.html
 * 
 * @struts.action path="/people" parameter="method" validate="false"
 *                input="/people.do" name="peopleForm"
 *
 * @struts.action-forward name="view-success" path="/WEB-INF/views/main/people.jsp"
 * 
 * @struts.action path="/add-people" parameter="method" validate="true"
 *                input="/WEB-INF/views/main/add-person.jsp" name="peopleForm"
 *
 * @struts.action-forward name="add-new" path="/WEB-INF/views/main/add-person.jsp"
 *
 * @struts.action-forward name="add-reset" path="/people.do?method=addnew" redirect="true"
 *
 * @struts.action-forward name="add-success" path="/people.do" redirect="true"
 *
 * @struts.action path="/update-people" parameter="method" validate="true"
 *                input="/WEB-INF/views/main/update-person.jsp" name="peopleForm"
 * 
 * @struts.action-forward name="update-existing" path="/WEB-INF/views/main/update-person.jsp"
 *
 * @struts.action-forward name="update-success" path="/people.do" redirect="true"
 *
 * @struts.action-forward name="delete-success" path="/people.do" redirect="true"
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

	public ActionForward addnew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("add-new");
	}

	public ActionForward addreset(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("add-reset");
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
		return addnew(mapping, peopleForm, request, response);
	}

	public ActionForward updateexisting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PeopleForm peopleForm = (PeopleForm) form;
		String id = peopleForm.getId();
		Person person;
		try {
			person = ActionServlet.getPeopleService().getPerson(id);
		} catch (PeopleException e) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
			saveMessages(request, messages);
			return view(mapping, form, request, response);
		}
		peopleForm.setFirstName(person.getFirstName());
		peopleForm.setLastName(person.getLastName());
		return mapping.findForward("update-existing");
	}

	public ActionForward updatereset(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return updateexisting(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PeopleForm peopleForm = (PeopleForm) form;
		boolean isValid = peopleForm.isValid(mapping, request);
		if (isValid) {
			try {
				Person updatedPerson = new Person();
				updatedPerson.setId(UUID.fromString(peopleForm.getId()));
				updatedPerson.setFirstName(peopleForm.getFirstName());
				updatedPerson.setLastName(peopleForm.getLastName());
				ActionServlet.getPeopleService().updatePerson(updatedPerson);
				return mapping.findForward("update-success");
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return updateexisting(mapping, peopleForm, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PeopleForm peopleForm = (PeopleForm) form;
		boolean isValid = peopleForm.getId() != null;
		if (isValid) {
			try {
				ActionServlet.getPeopleService().deletePerson(peopleForm.getId());
				return mapping.findForward("delete-success");
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return view(mapping, peopleForm, request, response);
	}
}
