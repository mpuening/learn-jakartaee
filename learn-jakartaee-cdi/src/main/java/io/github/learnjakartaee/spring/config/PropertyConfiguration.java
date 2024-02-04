package io.github.learnjakartaee.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.learnjakartaee.env.Environment;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class PropertyConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer profileProperties() {
		String profiles = System.getenv(Environment.PROFILES_ENV_VAR_NAME);
		if (profiles == null) {
			profiles = System.getProperty(Environment.PROFILES_PROPERTY_NAME, "");
		}
		List<Resource> resources = new ArrayList<>();
		resources.add(new ClassPathResource(String.format("application.properties")));
		for (String profile : profiles.split(",")) {
			resources.add(new ClassPathResource(String.format("application-%s.properties", profile)));
		}

		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		ppc.setLocations(resources.toArray(new Resource[resources.size()]));
		ppc.setIgnoreResourceNotFound(true);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		ppc.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return ppc;
	}
}
