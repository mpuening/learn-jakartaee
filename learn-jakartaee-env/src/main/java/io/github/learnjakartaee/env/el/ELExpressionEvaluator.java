package io.github.learnjakartaee.env.el;

import java.util.Map;
import java.util.Properties;

import io.github.learnjakartaee.env.ExpressionEvaluator;
import jakarta.el.ELProcessor;

public class ELExpressionEvaluator implements ExpressionEvaluator {

	private final ELProcessor processor;

	public ELExpressionEvaluator() {
		this.processor = new ELProcessor();
		processor.defineBean("env", getEnv());
		processor.defineBean("properties", getProperties());
	}

	@Override
	public String evaluateExpression(String description, String expression) {
		// LOG.debug("Evaluate " + description + ": " + expression);
		String value = processor.eval(expression).toString();
		return value;
	}

	public Map<String, String> getEnv() {
		return System.getenv();
	}

	public Properties getProperties() {
		return System.getProperties();
	}
}
