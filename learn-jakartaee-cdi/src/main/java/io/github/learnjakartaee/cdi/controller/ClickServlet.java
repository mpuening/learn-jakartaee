package io.github.learnjakartaee.cdi.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import io.github.learnjakartaee.cdi.config.CDICounter;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cdi/clicks/*")
public class ClickServlet extends HttpServlet {

	private static final long serialVersionUID = 3892187441638408457L;

	@Inject
	@CDICounter.AppScopedCounter
	CDICounter counter;

	@Inject
	@CDICounter.RequestScopedCounter
	CDICounter requestScopedCounter;

	@Inject
	@CDICounter.SessionScopedCounter
	CDICounter sessionScopedCounter;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if ("/app-click-count".equals(pathInfo)) {
			appClickCount(request, response);
		}
		else if ("/request-click-count".equals(pathInfo)) {
			requestClickCount(request, response);
		}
		else if ("/session-click-count".equals(pathInfo)) {
			sessionClickCount(request, response);
		}
		else {
			// Should use 404
			request.getRequestDispatcher("/index.html").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void appClickCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeResult(response, String.valueOf(counter.incrementAndGet()));
	}

	private void requestClickCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeResult(response, String.valueOf(requestScopedCounter.incrementAndGet()));
	}

	private void sessionClickCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeResult(response, String.valueOf(sessionScopedCounter.incrementAndGet()));
	}

	private void writeResult(HttpServletResponse response, String value) throws IOException {
		response.setStatus(200);
		response.setContentType("text/plain");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), Charset.forName("UTF-8"));
		out.write(value + " clicks");
		out.flush();
	}
}
