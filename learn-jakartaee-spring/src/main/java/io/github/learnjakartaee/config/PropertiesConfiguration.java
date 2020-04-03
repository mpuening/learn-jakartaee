package io.github.learnjakartaee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropertiesConfiguration {

	@Configuration
	@Profile("default")
	@PropertySource("classpath:application.properties")
	public static class DefaultConfiguration {

	}

	@Configuration
	@Profile("dev")
	@PropertySource(value = { "classpath:application.properties", "classpath:application-dev.properties" })
	public static class DevConfiguration {

	}

	@Configuration
	@Profile("prod")
	@PropertySource(value = { "classpath:application.properties", "classpath:application-prod.properties" })
	public static class ProdConfiguration {

	}
}
