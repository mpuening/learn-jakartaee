package io.github.learnjakartaee.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import io.github.learnjakartaee.sql.AppDataSource;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/**
 * Set up a data source for the application. Because this is in an EJB class
 * loader GlassFish behaves oddly and differently from WebApp class loaders, as
 * well as differently from other application servers. It does not call the
 * setPassword() method on the data source, but instead uses getConnection(u,p)
 * which our DataSource implementation does not support. Hence, you see the
 * password piggy-backing on the user name property.
 */
@DataSourceDefinition(
		name = "java:app/env/jdbc/appDataSource",
		className = "io.github.learnjakartaee.sql.SpelConfiguredDataSource",
		url = "env['DB_URL'] ?: properties['db.url'] ?: 'jdbc:derby:memory:appdb%3Bcreate=true'",
		user = "env['DB_USERNAME'] ?: properties['db.user'] ?: 'APP' / "
				+ "env['DB_PASSWORD'] ?: properties['db.password'] ?: ''",
		password = "env['DB_PASSWORD'] ?: properties['db.password'] ?: ''",
		properties = {
				"driverClassName=env['DB_DRIVER'] ?: properties['db.driver'] ?: 'org.apache.derby.jdbc.EmbeddedDriver'"
		})
@ApplicationScoped
@ManagedBean
public class DataSourceConfiguration {

	@Produces
	@AppDataSource
	public DataSource getDataSource() {
		return lookupDataSource();
	}

	/**
	 * The @Resource mechanism does not consistently work across all the application
	 * servers. However, the direct JNDI lookup does work. So we expose the CDI bean
	 * this way.
	 * 
	 * @return dataSource
	 */
	public static DataSource lookupDataSource() {
		try {
			InitialContext context = new InitialContext();
			Object o = context.lookup("java:app/env/jdbc/appDataSource");
			if (o instanceof DataSource) {
				DataSource dataSource = (DataSource) o;
				return dataSource;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}