package io.github.learnjakartaee.env.env;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.test.WebAppWarBuilder;

@ExtendWith(ArquillianExtension.class)
public class EnvEnvTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "mock,env,test");

		System.setProperty("aaa", "aaa-env-value");

		return new WebAppWarBuilder("learn-jakartaee-env")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@Test
	public void testEnvEnvironment() {
		ExpressionEvaluator evaluator = new EnvExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(EnvEnvTest.class.getClassLoader(), evaluator);
		assertEquals("default-value", environment.getProperty("SomeValue", "some.value"));
		assertEquals("env-value", environment.getProperty("SomeProfileValue", "some.profile.value"));
		assertEquals("aaa-env-value", environment.getProperty("SomeExpressionValue", "some.profile.expression"));
		assertEquals("bbb-env-value", environment.getProperty("SomeExpressionValueWithDefault", "some.profile.expression.with.default"));
	}
}
