package io.github.learnjakartaee.controller.admin;

import jakarta.mvc.Controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("admin")
public class AdminController {

	@GET
	@Path("events")
	@Controller
	@Produces("text/html")
	public String eventsPage() {
		return "admin/events.jsp";
	}

	@GET
	@Path("support")
	@Controller
	@Produces("text/html")
	public String supportPage() {
		return "admin/support.jsp";
	}
}
