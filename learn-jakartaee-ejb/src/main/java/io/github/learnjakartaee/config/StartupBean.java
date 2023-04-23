package io.github.learnjakartaee.config;

import javax.sql.DataSource;

import io.github.learnjakartaee.flyway.FlywayMigration;
import io.github.learnjakartaee.sql.AppDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;

/**
 * A start up bean to run the Flyway migrations. Flyway runs its own
 * transactions, hence this bean has bean managed transactions
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class StartupBean {

	@Inject
	@AppDataSource
	DataSource dataSource;

	@PostConstruct
	public void onStartup() {
		// It's not good practice to have an app be responsible to run migrations.
		// But it is quite convenient.
		FlywayMigration.run(dataSource, DataSourceConfiguration.class.getClassLoader());
	}
}
