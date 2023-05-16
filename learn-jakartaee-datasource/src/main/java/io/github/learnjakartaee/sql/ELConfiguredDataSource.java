package io.github.learnjakartaee.sql;

import java.util.Map;
import java.util.Properties;

import jakarta.el.ELProcessor;

/**
 * This is an configurable DataSource extension that is capable of looking up
 * database connection information from environment variables or system
 * properties using a Jakarta EL expression.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class ELConfiguredDataSource extends AbstractConfiguredDataSource {

	@Override
	protected String evaluateExpression(String property, String expression) {
		// LOG.debug("Evaluate " + property + ": " + expression);
		ELProcessor processor = new ELProcessor();
		processor.defineBean("env", getEnv());
		processor.defineBean("properties", getProperties());
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
