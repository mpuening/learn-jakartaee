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
	@Profile("local")
	@PropertySource(value = { "classpath:application.properties", "classpath:application-local.properties" })
	public static class LocalConfiguration {

	}

	@Configuration
	@Profile("dev")
	@PropertySource(value = { "classpath:application.properties", "classpath:application-dev.properties" })
	public static class DevConfiguration {

	}

	@Configuration
	@Profile("devliberty")
	@PropertySource(value = { "classpath:application.properties", "classpath:application-devliberty.properties" })
	public static class LibertyConfiguration {

	}
}
