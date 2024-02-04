package io.github.learnjakartaee.cdi.config;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import io.github.learnjakartaee.shared.model.Animal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AnimalConfiguration {

	@Produces
	public Animal animal() throws Exception {
		ExpressionEvaluator evaluator = new ELExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(AnimalConfiguration.class.getClassLoader(), evaluator);
		String animalClassName = environment.getProperty("AnimalName", "animal.classname");
		return (Animal) Class.forName(animalClassName).getDeclaredConstructor().newInstance();
	}
}
