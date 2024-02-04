package org.example.sql.config.ognl;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ognl.OgnlExpressionEvaluator;
import io.github.learnjakartaee.sql.EnvConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class OgnlConfigurableDataSource extends EnvConfigurableDataSource {

	public OgnlConfigurableDataSource() {
		super(new ConfigurableEnvironment(OgnlDataSourceConfiguration.class.getClassLoader(), new OgnlExpressionEvaluator()),
				new OgnlExpressionEvaluator());
	}
}
