package io.github.learnjakartaee.env.ognl;

import java.util.Map;

import io.github.learnjakartaee.env.ExpressionEvaluator;
import ognl.Ognl;
import ognl.OgnlException;

public class OgnlExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public String evaluateExpression(String description, String expression) {
		// LOG.debug("Evaluate " + description + ": " + expression);
		try {
			Map<String, Object> vars = Map.of("env" , System.getenv(), "properties", System.getProperties());
			String value = String.valueOf(Ognl.getValue(Ognl.parseExpression(expression), vars));
			return value;
		} catch (OgnlException e) {
			e.printStackTrace();
			return null;
		}
	}
}
