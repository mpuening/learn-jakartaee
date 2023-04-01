package io.github.learnjakartaee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.learnjakartaee.spring.config.SpringCounter;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ClickController {

	@Autowired
	@Qualifier("springCounter")
	protected SpringCounter counter;

	@Autowired
	@Qualifier("requestScopedSpringCounter")
	protected SpringCounter requestScopedCounter;

	@Autowired
	@Qualifier("sessionScopedSpringCounter")
	protected SpringCounter sessionScopedCounter;

	@GetMapping("/app-click-count")
	@ResponseBody
	public String appClickCount(HttpServletResponse response) {
		response.setContentType("text/plain");
		return String.valueOf(counter.incrementAndGet()) + " clicks";
	}

	@GetMapping("/request-click-count")
	@ResponseBody
	public String requestClickCount(HttpServletResponse response) {
		response.setContentType("text/plain");
		return String.valueOf(requestScopedCounter.incrementAndGet()) + " clicks";
	}

	@GetMapping("/session-click-count")
	@ResponseBody
	public String sessionClickCount(HttpServletResponse response) {
		response.setContentType("text/plain");
		return String.valueOf(sessionScopedCounter.incrementAndGet() + " clicks");
	}
}
