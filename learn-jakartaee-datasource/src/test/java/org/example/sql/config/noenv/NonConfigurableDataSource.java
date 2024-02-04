package org.example.sql.config.noenv;

import io.github.learnjakartaee.sql.ConfigurableDataSource;

/**
 * This is a configurable DataSource extension that is capable of looking up
 * database connection information from the environment / profiles config
 * properties.
 *
 * See the DataSourceConfiguration class for example usage.
 */
public class NonConfigurableDataSource extends ConfigurableDataSource {

	@Override
	protected String evaluateValue(String description, String value) {
		// Let's not be configurable at all...
		return value;
	}

}
