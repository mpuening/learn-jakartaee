package io.github.learnjakartaee.env.spel;

import java.util.Map;
import java.util.Properties;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import io.github.learnjakartaee.env.ExpressionEvaluator;

public class SpelExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public String evaluateExpression(String description, String expression) {
		// LOG.debug("Evaluate " + description + ": " + expression);
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression parsed = expressionParser.parseExpression(expression);

		EvaluationContext context = new StandardEvaluationContext(this);
		String value = String.valueOf(parsed.getValue(context));
		return value;
	}

	public Map<String, String> getEnv() {
		return System.getenv();
	}

	public Properties getProperties() {
		return System.getProperties();
	}
}
