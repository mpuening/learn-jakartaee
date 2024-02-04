package io.github.learnjakartaee.env.spel;

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
public class SpelEnvTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "mock,spel,test");

		System.setProperty("aaa", "aaa-spel-value");

		return new WebAppWarBuilder("learn-jakartaee-env")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@Test
	public void testSpelEnvironment() {
		ExpressionEvaluator evaluator = new SpelExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(SpelEnvTest.class.getClassLoader(), evaluator);
		assertEquals("default-value", environment.getProperty("SomeValue", "some.value"));
		assertEquals("spel-value", environment.getProperty("SomeProfileValue", "some.profile.value"));
		assertEquals("aaa-spel-value", environment.getProperty("SomeExpressionValue", "some.profile.expression"));
		assertEquals("bbb-spel-value", environment.getProperty("SomeExpressionValueWithDefault", "some.profile.expression.with.default"));
	}
}
