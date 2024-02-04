package org.example.sql.config.spel;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.spel.SpelExpressionEvaluator;
import io.github.learnjakartaee.sql.EnvConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class SpelConfigurableDataSource extends EnvConfigurableDataSource {

	public SpelConfigurableDataSource() {
		super(new ConfigurableEnvironment(SpelDataSourceConfiguration.class.getClassLoader(), new SpelExpressionEvaluator()),
				new SpelExpressionEvaluator());
	}
}
