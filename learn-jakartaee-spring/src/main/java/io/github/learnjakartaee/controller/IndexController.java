package io.github.learnjakartaee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.learnjakartaee.service.MessageService;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	protected  MessageService messageService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("message", messageService.getGreetingMessage());
		return "index";
	}
}
