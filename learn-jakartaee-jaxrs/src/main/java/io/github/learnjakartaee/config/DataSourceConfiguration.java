package io.github.learnjakartaee.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

/**
 * https://db.apache.org/derby/docs/10.1/publishedapi/org/apache/derby/jdbc/EmbeddedDataSource.html
 */
@DataSourceDefinition(
		name = "java:app/env/jdbc/appDataSource",
		className = "org.apache.derby.jdbc.EmbeddedDataSource",
		databaseName = "memory:appdb",
		user = "APP",
		password = "",
		properties = {
				"createDatabase=create"
		})
@ApplicationScoped
public class DataSourceConfiguration {

	@Resource(lookup = "java:app/env/jdbc/appDataSource")
	DataSource dataSource;

	@Produces
	public DataSource getDatasource() {
		return dataSource;
	}

	public void onStart(@Observes @Initialized(ApplicationScoped.class) Object unused) {
		// It's not good practice to have an app be responsible to run migrations.
		// But it is quite convenient.
		runFlywayMigration();
	}

	private static boolean flywayMigrationComplete = false;

	public static boolean isFlywayMigrationComplete() {
		return flywayMigrationComplete;
	}

	protected void runFlywayMigration() {
		try {
			if (dataSource != null && !flywayMigrationComplete) {
				Flyway flyway = Flyway
						.configure()
						.dataSource(dataSource)
						.baselineOnMigrate(true)
						.load();
				MigrateResult result = flyway.migrate();
				flywayMigrationComplete = result.success;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
