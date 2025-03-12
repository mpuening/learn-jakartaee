package io.github.learnjakartaee.config;

import io.github.learnjakartaee.aircraft.service.AppAircraftService;
import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.annotation.FacesConfig;

@FacesConfig
@ApplicationScoped
public class ApplicationConfiguration {

	@Produces
	public AppAircraftService aircraftService() throws Exception {
		ExpressionEvaluator evaluator = new ELExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
		String serviceClassName = environment.getProperty("AircraftService", "jsf.aircraft.service.classname", "io.github.learnjakartaee.aircraft.service.MockAircraftService");
		return (AppAircraftService) Class.forName(serviceClassName).getDeclaredConstructor().newInstance();
	}
}
