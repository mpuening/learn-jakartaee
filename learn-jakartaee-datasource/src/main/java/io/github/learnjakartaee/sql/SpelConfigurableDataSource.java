package io.github.learnjakartaee.sql;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.spel.SpelExpressionEvaluator;

/**
 * This is a configurable DataSource that is capable of looking up database
 * connection information from the environment / profiles config properties as
 * process values as spring expressions.
 */
public class SpelConfigurableDataSource extends ConfigurableDataSource {

	public SpelConfigurableDataSource() {
		super(new ConfigurableEnvironment(new String[] { "application", "datasource", "security" }, new SpelExpressionEvaluator()),
				new SpelExpressionEvaluator());
	}
}
