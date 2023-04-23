package io.github.learnjakartaee.sql;

/**
 * This is an configurable DataSource extension that is capable of looking up database
 * connection information from environment variables or system properties.
 * Variable names are indicated by starting with '$'. Default values are
 * supported by having a value coming after a ":" that is after the variable
 * name. For example: $DB_USERNAME:username
 *
 * This class assumes env names would be named the same as system properties which
 * is usually not the case. That is why the OGNL version exists.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class EnvConfiguredDataSource extends AbstractConfiguredDataSource {

	protected String evaluateExpression(String property, String expression) {
		//LOG.debug("Evaluate " + property + ": " + expression);
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
