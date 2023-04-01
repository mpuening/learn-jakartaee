package io.github.learnjakartaee.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class CounterConfiguration {

	// @Autowired
	// private ApplicationContext applicationContext;

	@Bean("springCounter")
	public SpringCounter springCounter() {
		return new SpringCounter();
	}

	@Bean("requestScopedSpringCounter")
	@RequestScope
	public SpringCounter requestScopedSpringCounter() {
		return new SpringCounter();
	}

	@Bean("sessionScopedSpringCounter")
	@SessionScope
	public SpringCounter sessionScopedSpringCounter() {
		return new SpringCounter();
	}
}
