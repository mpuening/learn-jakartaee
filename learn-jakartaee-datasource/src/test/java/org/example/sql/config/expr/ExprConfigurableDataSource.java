package org.example.sql.config.expr;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import io.github.learnjakartaee.sql.ConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class ExprConfigurableDataSource extends ConfigurableDataSource {

	public ExprConfigurableDataSource() {
		super(new ConfigurableEnvironment(new ELExpressionEvaluator()),
				new ELExpressionEvaluator());
	}
}
