package io.github.learnjakartaee.config;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import io.github.learnjakartaee.sql.EnvConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 */
public class ELConfigurableDataSource extends EnvConfigurableDataSource {

	public ELConfigurableDataSource() {
		super(new ConfigurableEnvironment("datasource", DataSourceConfiguration.class.getClassLoader(), new ELExpressionEvaluator()),
				new ELExpressionEvaluator());
	}
}
