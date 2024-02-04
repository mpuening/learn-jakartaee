package org.example.sql.config.ognl;

import javax.sql.DataSource;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.env.ognl.OgnlExpressionEvaluator;
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
		className = "org.example.sql.config.ognl.OgnlConfigurableDataSource",
		url = "ENV(db.url)",
		user = "ENV(db.user)/ENV(db.password)",
		password = "ENV(db.password)",
		properties = {
				"driverClassName=ENV(db.driver)"
		})
@ApplicationScoped
public class OgnlDataSourceConfiguration {

	@Resource(lookup = "java:app/env/jdbc/appDataSource")
	DataSource dataSource;

	@Produces
	@AppDataSource
	public DataSource getDatasource() {
		return dataSource;
	}

	public void onStart(@Observes @Initialized(ApplicationScoped.class) Object unused) {
		// It's not good practice to have an app be responsible to run migrations.
		// But it is quite convenient for local development
		ExpressionEvaluator evaluator = new OgnlExpressionEvaluator();
		Environment environment = new ConfigurableEnvironment(OgnlDataSourceConfiguration.class.getClassLoader(), evaluator);
		if ("true".equalsIgnoreCase(environment.getProperty("FlyWay", "flyway.migration.enabled", "false"))) {
			FlywayMigration.run(dataSource, OgnlDataSourceConfiguration.class.getClassLoader());			
		}
		else {
			FlywayMigration.setFlywayMigrationSkipped();
		}
	}

}
