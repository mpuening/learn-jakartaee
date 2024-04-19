package io.github.learnjakartaee.controller.main;

import jakarta.mvc.Controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("main")
public class MainController {

	@GET
	@Path("help")
	@Controller
	@Produces("text/html")
	public String helpPage() {
		return "main/help.jsp";
	}

	@GET
	@Path("aircraft")
	@Controller
	@Produces("text/html")
	public String aircraftPage() {
		return "main/aircraft.jsp";
	}

}
