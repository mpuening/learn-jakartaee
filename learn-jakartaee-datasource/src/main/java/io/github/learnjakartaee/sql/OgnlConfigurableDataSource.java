package io.github.learnjakartaee.sql;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ognl.OgnlExpressionEvaluator;

/**
 * This is a configurable DataSource that is capable of looking up database
 * connection information from the environment / profiles config properties as
 * process values as OGNL expressions.
 */
public class OgnlConfigurableDataSource extends ConfigurableDataSource {

	public OgnlConfigurableDataSource() {
		super(new ConfigurableEnvironment(new String[] { "application", "datasource" }, new OgnlExpressionEvaluator()),
				new OgnlExpressionEvaluator());
	}
}
