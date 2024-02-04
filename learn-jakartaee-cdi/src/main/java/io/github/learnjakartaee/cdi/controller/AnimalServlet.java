package io.github.learnjakartaee.cdi.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import io.github.learnjakartaee.shared.model.Animal;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cdi/animal-speak")
public class AnimalServlet extends HttpServlet {

	private static final long serialVersionUID = 3459797345508439630L;

	@Inject
	Animal animal;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		writeResult(response, animal.getClass().getSimpleName() + " says " + animal.speak());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void writeResult(HttpServletResponse response, String value) throws IOException {
		response.setStatus(200);
		response.setContentType("text/plain");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), Charset.forName("UTF-8"));
		out.write(value);
		out.flush();
	}
}
