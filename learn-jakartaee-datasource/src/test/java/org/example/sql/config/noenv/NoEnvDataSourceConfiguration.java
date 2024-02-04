package org.example.sql.config.noenv;

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
		className = "org.example.sql.config.noenv.NonConfigurableDataSource",
		url = "jdbc:derby:memory:appdb%3Bcreate=true",
		user = "APP",
		password = "",
		properties = {
				"driverClassName=org.apache.derby.jdbc.EmbeddedDriver"
		})
@ApplicationScoped
public class NoEnvDataSourceConfiguration {

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
		FlywayMigration.run(getDatasource(), NoEnvDataSourceConfiguration.class.getClassLoader());
	}

}
