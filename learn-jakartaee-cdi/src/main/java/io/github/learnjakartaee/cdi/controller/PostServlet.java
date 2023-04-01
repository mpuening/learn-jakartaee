package io.github.learnjakartaee.cdi.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import io.github.learnjakartaee.cdi.service.PostService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cdi/posts")
public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = 8162210955232460740L;

	@Inject
	PostService postService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(200);
		response.setContentType("text/plain");
		final OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), Charset.forName("UTF-8"));
		out.write(postService.getPostListing());
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = request.getParameter("message");
		if (message != null && !message.isEmpty()) {
			postService.publishPost(message);
		}
		doGet(request, response);
	}
}
