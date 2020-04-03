package io.github.learnjakartaee.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Value("${visitor.greeting}")
	protected String greeting;

	public String getGreetingMessage() {
		return this.greeting;
	}
}
