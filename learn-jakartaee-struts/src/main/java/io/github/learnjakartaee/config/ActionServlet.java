package io.github.learnjakartaee.config;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import io.github.learnjakartaee.aircraft.service.AircraftService;

/**
 * Extends the action servlet in order to be able to have access to CDI beans
 */
public class ActionServlet extends org.apache.struts.action.ActionServlet {

	private static final long serialVersionUID = -2647620185014768610L;

	private static ActionServlet singleton;

	public ActionServlet() {
		ActionServlet.singleton = this;
	}

	@Inject
	private SecurityContext securityContext;

	@Inject @EJB
	protected AircraftService aircraftService;

	public static SecurityContext getSecurityContext() {
		return ActionServlet.singleton.securityContext;
	}

	public static AircraftService getAircraftService() {
		return ActionServlet.singleton.aircraftService;
	}
}
