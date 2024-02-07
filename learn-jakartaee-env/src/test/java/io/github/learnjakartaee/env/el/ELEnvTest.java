package io.github.learnjakartaee.env.el;

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
public class ELEnvTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "mock,el,test");

		System.setProperty("aaa", "aaa-el-value");

		return new WebAppWarBuilder("learn-jakartaee-env")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@Test
	public void testELEnvironment() {
		ExpressionEvaluator evaluator = new ELExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
		assertEquals("default-value", environment.getProperty("SomeValue", "some.value"));
		assertEquals("el-value", environment.getProperty("SomeProfileValue", "some.profile.value"));
		assertEquals("aaa-el-value", environment.getProperty("SomeExpressionValue", "some.profile.expression"));
		assertEquals("bbb-el-value", environment.getProperty("SomeExpressionValueWithDefault", "some.profile.expression.with.default"));
	}
}
