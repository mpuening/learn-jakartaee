package io.github.learnjakartaee.env.ognl;

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
public class OgnlEnvTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "mock,ognl,test");

		System.setProperty("aaa", "aaa-ognl-value");

		return new WebAppWarBuilder("learn-jakartaee-env")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@Test
	public void testOgnlEnvironment() {
		ExpressionEvaluator evaluator = new OgnlExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(OgnlEnvTest.class.getClassLoader(), evaluator);
		assertEquals("default-value", environment.getProperty("SomeValue", "some.value"));
		assertEquals("ognl-value", environment.getProperty("SomeProfileValue", "some.profile.value"));
		assertEquals("aaa-ognl-value", environment.getProperty("SomeExpressionValue", "some.profile.expression"));
		assertEquals("bbb-ognl-value", environment.getProperty("SomeExpressionValueWithDefault", "some.profile.expression.with.default"));
	}
}
