package io.github.learnjakartaee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.learnjakartaee.spring.service.PostService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PostController {

	@Autowired
	PostService postService;

	@GetMapping("/posts")
	@ResponseBody
	public String showPosts(HttpServletResponse response) {
		response.setContentType("text/plain");
		return postService.getPostListing();
	}

	@PostMapping(value = "/posts", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String publishPost(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) {
		String message = formData.getFirst("message");
		if (message != null && !message.isEmpty()) {
			postService.publishPost(message);
		}
		return showPosts(response);
	}

}
