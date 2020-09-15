package io.github.learnjakartaee.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.learnjakartaee.model.Person;
import io.github.learnjakartaee.service.PeopleService;

@WebServlet(urlPatterns = "/people")
public class PeopleServlet extends HttpServlet {

	private static final long serialVersionUID = 3892187441638408457L;
	
	@Inject
	private PeopleService personService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Person> people = personService.getPeople();
		request.setAttribute("people", people);
		request.getRequestDispatcher("/WEB-INF/views/main/people.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name != null && !name.isBlank()) {
			personService.addPerson(name);
		}
		response.sendRedirect(request.getContextPath() + "/people");
	}
}
