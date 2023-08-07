package io.github.learnjakartaee.aircraft;

import java.io.IOException;
import java.util.List;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import io.github.learnjakartaee.aircraft.service.AircraftService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/aircraft")
public class AircraftServlet extends HttpServlet {

	private static final long serialVersionUID = -4662566462303149763L;

	@EJB
	protected AircraftService aircraftService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Aircraft> aircraft = aircraftService.findAll();
		request.setAttribute("aircraftList", aircraft);
		request.getRequestDispatcher("/WEB-INF/views/main/aircraft.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
