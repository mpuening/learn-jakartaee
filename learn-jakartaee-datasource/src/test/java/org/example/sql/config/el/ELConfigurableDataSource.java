package org.example.sql.config.el;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import io.github.learnjakartaee.sql.EnvConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class ELConfigurableDataSource extends EnvConfigurableDataSource {

	public ELConfigurableDataSource() {
		super(new ConfigurableEnvironment(ELDataSourceConfiguration.class.getClassLoader(), new ELExpressionEvaluator()),
				new ELExpressionEvaluator());
	}
}
