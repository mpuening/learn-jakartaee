package org.example.sql.config.env;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.env.EnvExpressionEvaluator;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class EnvConfigurableDataSource extends io.github.learnjakartaee.sql.EnvConfigurableDataSource {

	public EnvConfigurableDataSource() {
		super(new ConfigurableEnvironment(EnvDataSourceConfiguration.class.getClassLoader(), new EnvExpressionEvaluator()),
				new EnvExpressionEvaluator());
	}
}
