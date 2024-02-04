package org.example.sql.config.expr;

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
public class ExprConfigurableDataSource extends EnvConfigurableDataSource {

	public ExprConfigurableDataSource() {
		super(new ConfigurableEnvironment(ExprDataSourceConfiguration.class.getClassLoader(), new ELExpressionEvaluator()),
				new ELExpressionEvaluator());
	}
}
