package io.github.learnjakartaee.env.env;

import io.github.learnjakartaee.env.ExpressionEvaluator;

public class EnvExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public String evaluateExpression(String description, String expression) {
		// LOG.debug("Evaluate " + description + ": " + expression);
		if (expression != null && expression.startsWith("$")) {
			String key = expression.substring(1);
			String defaultValue = key;
			int hasDefault = expression.indexOf(':');
			if (hasDefault >= 0) {
				key = expression.substring(1, hasDefault);
				defaultValue = expression.substring(hasDefault + 1);
			}
			String envValue = System.getenv(key);
			if (envValue != null) {
				expression = envValue;
			} else {
				expression = System.getProperty(key, defaultValue);
			}
		}
		return expression;
	}
}
