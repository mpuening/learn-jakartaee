package org.example.sql.config.expr;

import javax.sql.DataSource;

import io.github.learnjakartaee.flyway.FlywayMigration;
import io.github.learnjakartaee.sql.AppDataSource;
import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

// Environment variables

//@DataSourceDefinition(
//		name = "java:app/env/jdbc/appDataSource",
//		className = "org.example.sql.config.expr.ExprConfigurableDataSource",
//		url = "EVAL($DB_URL:jdbc:derby:memory:appdb%3Bcreate=true)",
//		user = "EVAL($DB_USERNAME:APP)",
//		password = "EVAL($DB_PASSWORD:)",
//		properties = {
//			"driverClassName=EVAL($DB_DRIVER:org.apache.derby.jdbc.EmbeddedDriver)"
//})

// Ognl Expressions

//@DataSourceDefinition(
//		name = "java:app/env/jdbc/appDataSource",
//		className = "org.example.sql.config.expr.ExprConfigurableDataSource",
//		url = "EVAL(env.get('DB_URL') != null ? env.get('DB_URL') : "
//			+ "properties.getOrDefault('db.url', 'jdbc:derby:memory:appdb%3Bcreate=true'))",
//		user = "EVAL(env.get('DB_USERNAME') != null ? env.get('DB_USERNAME') : "
//			+ "properties.getOrDefault('db.user', 'APP'))",
//		password = "EVAL(env.get('DB_PASSWORD') != null ? env.get('DB_PASSWORD') : "
//			+ "properties.getOrDefault('db.password', ''))",
//		properties = {
//			"driverClassName=EVAL(env.get('DB_DRIVER') != null ? env.get('DB_DRIVER') : "
//				+ "properties.getOrDefault('db.driver', 'org.apache.derby.jdbc.EmbeddedDriver'))"
//})

// Spel Expressions

//@DataSourceDefinition(
//		name = "java:app/env/jdbc/appDataSource",
//		className = "org.example.sql.config.expr.ExprConfigurableDataSource",
//		url = "EVAL(env['DB_URL'] ?: properties['db.url'] ?: 'jdbc:derby:memory:appdb%3Bcreate=true')",
//		user = "EVAL(env['DB_USERNAME'] ?: properties['db.user'] ?: 'APP')",
//		password = "EVAL(env['DB_PASSWORD'] ?: properties['db.password'] ?: '')",
//		properties = {
//			"driverClassName=EVAL(env['DB_DRIVER'] ?: properties['db.driver'] ?: 'org.apache.derby.jdbc.EmbeddedDriver')"
//})

// EL Expressions

@DataSourceDefinition(
		name = "java:app/env/jdbc/appDataSource",
		className = "org.example.sql.config.expr.ExprConfigurableDataSource",
		url = "EVAL(not empty env.get('DB_URL') ? env.get('DB_URL') : "
				+ "properties.getOrDefault('db.url', 'jdbc:derby:memory:appdb%3Bcreate=true'))",
		user = "EVAL(not empty env.get('DB_USERNAME') ? env.get('DB_USERNAME') : "
				+ "properties.getOrDefault('db.user', 'APP'))/"
				+ "EVAL(not empty env.get('DB_PASSWORD') ? env.get('DB_PASSWORD') : "
				+ "properties.getOrDefault('db.password', ''))",
		password = "EVAL(not empty env.get('DB_PASSWORD') ? env.get('DB_PASSWORD') : "
				+ "properties.getOrDefault('db.password', ''))",
		properties = {
				"driverClassName=EVAL(not empty env.get('DB_DRIVER') ? env.get('DB_DRIVER') : "
						+ "properties.getOrDefault('db.driver', 'org.apache.derby.jdbc.EmbeddedDriver'))"
})
@ApplicationScoped
public class ExprDataSourceConfiguration {

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
		FlywayMigration.run(getDatasource(), ExprDataSourceConfiguration.class.getClassLoader());
	}

}
