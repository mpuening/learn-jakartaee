package io.github.learnjakartaee.sql;

import java.util.Map;
import java.util.Properties;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * This is an configurable DataSource extension that is capable of looking up
 * database connection information from environment variables or system
 * properties using an Spring SpEL expression.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class SpelConfiguredDataSource extends AbstractConfiguredDataSource {

	@Override
	protected String evaluateExpression(String property, String expression) {
		// LOG.debug("Evaluate " + property + ": " + expression);
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
