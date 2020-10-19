package io.github.learnjakartaee.config;

import jakarta.inject.Inject;

import io.github.learnjakartaee.service.PeopleService;

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
	protected PeopleService peopleService;

	public static PeopleService getPeopleService() {
		return ActionServlet.singleton.peopleService;
	}
}
