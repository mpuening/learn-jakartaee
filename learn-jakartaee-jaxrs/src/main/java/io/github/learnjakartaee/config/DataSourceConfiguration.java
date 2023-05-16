package io.github.learnjakartaee.config;

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
		className = "io.github.learnjakartaee.sql.ELConfiguredDataSource",
		url = "not empty env.get('DB_URL') ? env.get('DB_URL') : "
				+ "properties.getOrDefault('db.url', 'jdbc:derby:memory:appdb%3Bcreate=true')",
		user = "not empty env.get('DB_USERNAME') ? env.get('DB_USERNAME') : "
				+ "properties.getOrDefault('db.user', 'APP') / "
				+ "not empty env.get('DB_PASSWORD') ? env.get('DB_PASSWORD') : "
				+ "properties.getOrDefault('db.password', '')",
		password = "not empty env.get('DB_PASSWORD') ? env.get('DB_PASSWORD') : "
				+ "properties.getOrDefault('db.password', '')",
		properties = {
				"driverClassName=not empty env.get('DB_DRIVER') ? env.get('DB_DRIVER') : "
						+ "properties.getOrDefault('db.driver', 'org.apache.derby.jdbc.EmbeddedDriver')"
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
		FlywayMigration.run(getDatasource(), DataSourceConfiguration.class.getClassLoader());
	}
}
