package io.github.learnjakartaee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.learnjakartaee.shared.model.Animal;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AnimalController {

	@Autowired
	protected Animal animal;

	@GetMapping("/animal-speak")
	@ResponseBody
	public String speak(HttpServletResponse response) {
		response.setContentType("text/plain");
		return animal.getClass().getSimpleName() + " says " + animal.speak();
	}

}
