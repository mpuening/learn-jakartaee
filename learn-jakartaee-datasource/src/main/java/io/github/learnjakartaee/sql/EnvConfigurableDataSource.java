package io.github.learnjakartaee.sql;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.env.EnvExpressionEvaluator;

/**
 * This is a configurable DataSource that is capable of looking up database
 * connection information from the environment / profiles config properties as
 * process values as environment variables or system properties.
 */
public class EnvConfigurableDataSource extends ConfigurableDataSource {

	public EnvConfigurableDataSource() {
		super(new ConfigurableEnvironment(new String[] { "application", "datasource", "security" }, new EnvExpressionEvaluator()),
				new EnvExpressionEvaluator());
	}
}
