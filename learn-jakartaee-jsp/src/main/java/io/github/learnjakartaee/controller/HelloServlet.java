package io.github.learnjakartaee.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 3892187441638408457L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/main/hello.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name == null || name.isEmpty()) {
			request.setAttribute("greeting", "I'm sorry, I didn't catch your name...");
		}
		else {
			request.setAttribute("greeting", String.format("Hello %s, how are you today?", name));
		}
		doGet(request, response);
	}
}
