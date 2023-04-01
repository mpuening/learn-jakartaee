package io.github.learnjakartaee.shared.config;

import javax.sql.DataSource;

import io.github.learnjakartaee.flyway.FlywayMigration;
import io.github.learnjakartaee.sql.AppDataSource;
import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

@DataSourceDefinition(
		name = "java:app/env/jdbc/appDataSource",
		className = "io.github.learnjakartaee.sql.EnvironmentAwareDataSource",
		url = "$DB_URL:jdbc:derby:memory:appdb;create=true",
		user = "$DB_USERNAME:APP",
		password = "$DB_PASSWORD:",
		properties = {
				"driverClassName=$DB_DRIVER:org.apache.derby.jdbc.EmbeddedDriver"
		})
@ApplicationScoped
public class DataSourceConfiguration {

	@Resource(lookup = "java:app/env/jdbc/appDataSource")
	DataSource dataSource;

	@Produces
	@AppDataSource
	public DataSource getDatasource() {
		return dataSource;
	}

	public void onStart(@Observes @Initialized(ApplicationScoped.class) Object unused) {
		// It's not good practice to have an app be responsible to run migrations.
		// But it is quite convenient.
		FlywayMigration.run(dataSource, DataSourceConfiguration.class.getClassLoader());
	}
}
