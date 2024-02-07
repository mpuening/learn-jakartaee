package io.github.learnjakartaee.sql;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;

/**
 * This is a configurable DataSource that is capable of looking up database
 * connection information from the environment / profiles config properties as
 * process values as EL expressions.
 */
public class ELConfigurableDataSource extends ConfigurableDataSource {

	public ELConfigurableDataSource() {
		super(new ConfigurableEnvironment(new String[] { "application", "datasource" }, new ELExpressionEvaluator()),
				new ELExpressionEvaluator());
		
		ConfigurableEnvironment env = new ConfigurableEnvironment(new String[] { "application", "datasource" }, new ELExpressionEvaluator());
	}
}
