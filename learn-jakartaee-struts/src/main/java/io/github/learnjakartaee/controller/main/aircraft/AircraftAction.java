package io.github.learnjakartaee.controller.main.aircraft;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import io.github.learnjakartaee.config.ActionServlet;

/**
 * XDoclet documentation: http://xdoclet.sourceforge.net/xdoclet/tags/apache-tags.html
 * 
 * @struts.action path="/aircraft" parameter="method" validate="false"
 *                input="/aircraft.do" name="aircraftForm"
 *
 * @struts.action-forward name="view-success" path="/WEB-INF/views/main/aircraft.jsp"
 * 
 * @struts.action path="/add-aircraft" parameter="method" validate="true"
 *                input="/WEB-INF/views/main/add-aircraft.jsp" name="aircraftForm"
 *
 * @struts.action-forward name="add-new" path="/WEB-INF/views/main/add-aircraft.jsp"
 *
 * @struts.action-forward name="add-reset" path="/aircraft.do?method=addnew" redirect="true"
 *
 * @struts.action-forward name="add-success" path="/aircraft.do" redirect="true"
 *
 * @struts.action path="/update-aircraft" parameter="method" validate="true"
 *                input="/WEB-INF/views/main/update-aircraft.jsp" name="aircraftForm"
 * 
 * @struts.action-forward name="update-existing" path="/WEB-INF/views/main/update-aircraft.jsp"
 *
 * @struts.action-forward name="update-success" path="/aircraft.do" redirect="true"
 *
 * @struts.action-forward name="delete-success" path="/aircraft.do" redirect="true"
 */
public class AircraftAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AircraftForm aircraftForm = (AircraftForm) form;
		aircraftForm.setAircraft(ActionServlet.getAircraftService().findAll());
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
		AircraftForm aircraftForm = (AircraftForm) form;
		boolean isValid = aircraftForm.isValid(mapping, request);
		if (isValid) {
			try {
				Aircraft aircraft = aircraftForm.asAircraft();
				aircraft.setId(null);
				ActionServlet.getAircraftService().createAircraft(aircraft);
				return mapping.findForward("add-success");
			} catch (Exception e) {
				e.printStackTrace();
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return addnew(mapping, aircraftForm, request, response);
	}

	public ActionForward updateexisting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AircraftForm aircraftForm = (AircraftForm) form;
		String id = aircraftForm.getId();
		Aircraft aircraft;
		try {
			aircraft = ActionServlet.getAircraftService().findById(id).get();
		} catch (Exception e) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
			saveMessages(request, messages);
			return view(mapping, form, request, response);
		}
		aircraftForm.applyAircraft(aircraft);
		return mapping.findForward("update-existing");
	}

	public ActionForward updatereset(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return updateexisting(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AircraftForm aircraftForm = (AircraftForm) form;
		boolean isValid = aircraftForm.isValid(mapping, request);
		if (isValid) {
			try {
				Aircraft updatedAircraft = aircraftForm.asAircraft();
				ActionServlet.getAircraftService().updateAircraft(updatedAircraft);
				return mapping.findForward("update-success");
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return updateexisting(mapping, aircraftForm, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AircraftForm aircraftForm = (AircraftForm) form;
		boolean isValid = aircraftForm.getId() != null;
		if (isValid) {
			try {
				Aircraft deletedAircraft = aircraftForm.asAircraft();
				ActionServlet.getAircraftService().deleteAircraft(deletedAircraft);
				return mapping.findForward("delete-success");
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.nondescript", e.getMessage()));
				saveMessages(request, messages);
			}
		}
		return view(mapping, aircraftForm, request, response);
	}
}
