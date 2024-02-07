package io.github.learnjakartaee.env.mp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.env.ExpressionEvaluator;

public class MPEnvTest {

	@Test
	public void testMPEnvironment() {
		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "mock,mp,dev");
		ExpressionEvaluator evaluator = new MPExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
		assertNotNull(environment);
		
		String username = environment.getProperty("username", "db.user");
		assertEquals("APP", username);

		String absent = environment.getProperty("absent", "unknown.prop", "default");
		assertEquals("default", absent);
		
		String appName = environment.getProperty("AppName", "app.name");
		assertEquals("shadowed-app-name", appName);

		String evaluated = environment.getProperty("Evaluated", "eval.prop");
		assertEquals(null, evaluated);
	}
}
