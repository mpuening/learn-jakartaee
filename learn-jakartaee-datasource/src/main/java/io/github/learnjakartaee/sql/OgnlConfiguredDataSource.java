package io.github.learnjakartaee.sql;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * This is an configurable DataSource extension that is capable of looking up database
 * connection information from environment variables or system properties using
 * an OGNL expression. The environment variables are available in an 'env' variable
 * and the properties are available in an 'properties' variable.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class OgnlConfiguredDataSource extends AbstractConfiguredDataSource {

	@Override
	protected String evaluateExpression(String property, String expression) {
		//LOG.debug("Evaluate " + property + ": " + expression);
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
