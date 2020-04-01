package io.github.learnjakartaee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String index(Model model) {
		model.addAttribute("message", "Hello, thank you for visiting...");
		return "index";
	}
}
