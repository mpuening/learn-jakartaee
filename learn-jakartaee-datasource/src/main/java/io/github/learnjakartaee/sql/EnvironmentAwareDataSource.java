package io.github.learnjakartaee.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * This is an Hikari DataSource extension that is capable of looking up database
 * connection information from environment variables or system properties.
 * Variable names are indicated by starting with '$'. Default values are
 * supported by having a value coming after a ":" that is after the variable
 * name. For example: $DB_USERNAME:username
 *
 * Much more could be added to this class such as supporting values from
 * Micro-profile Config sources, JNDI names, or encryption. Also, not added to
 * this class are setters to externalize the connection pool settings, or having
 * a validation SQL statement. But all that should be fairly easy to add. I just
 * don't need it for this project.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class EnvironmentAwareDataSource extends HikariDataSource {

	private final Logger LOG = LoggerFactory.getLogger(EnvironmentAwareDataSource.class);

	public EnvironmentAwareDataSource() {
		this.setMinimumIdle(3);
	}

	@Override
	public void setDriverClassName(String driverClassName) {
		if (driverClassName != null) {
			super.setDriverClassName(evaluateExpression("driver", driverClassName));
		}
	}

	@Override
	public void setJdbcUrl(String jdbcUrl) {
		if (jdbcUrl != null) {
			super.setJdbcUrl(evaluateExpression("url", jdbcUrl));
		}
	}

	/**
	 * For GlassFish support, the username may appear as a delimited
	 * username/password string. GlassFish does not invoke the setPassword setter
	 * but instead call getConnection(u,p) which is not supported by Hikari.
	 * Doubling up the username as a username/password pair allows us to check the
	 * username and password in the getConnection(u,p) method against the configured
	 * credentials.
	 */
	@Override
	public void setUsername(String username) {
		if (username != null && username.contains("/")) {
			// Username appears as username/password pair
			String[] credentials = username.split("/");
			super.setUsername(evaluateExpression("username1/2", credentials[0]));
			super.setPassword(evaluateExpression("password2/2", credentials.length > 1 ? credentials[1] : ""));
		} else if (username != null) {
			super.setUsername(evaluateExpression("username", username));
		}
	}

	@Override
	public void setPassword(String password) {
		if (password != null) {
			super.setPassword(evaluateExpression("password", password));
		}
	}

	protected String evaluateExpression(String property, String expression) {
		LOG.info("Evaluate " + property + ": " + expression);
		System.out.println("Evaluate " + property + ": " + expression);
		if (expression != null && expression.startsWith("$")) {
			String key = expression.substring(1);
			String defaultValue = key;
			int hasDefault = expression.indexOf(':');
			if (hasDefault >= 0) {
				key = expression.substring(1, hasDefault);
				defaultValue = expression.substring(hasDefault + 1);
			}
			String envValue = System.getenv(key);
			if (envValue != null) {
				expression = envValue;
			} else {
				expression = System.getProperty(key, defaultValue);
			}
		}
		return expression;
	}

	// ==========================================
	// Bootstrap logic
	// ==========================================

	private static int tries = 3;

	protected void bootstrapConnectionPool() {
		while (!isRunning() && tries > 0) {
			LOG.info("HIKARI DB CONNECTION POOL FILLING...");
			try (Connection connection = super.getConnection()) {
				connection.getMetaData();
				LOG.info("HIKARI DB CONNECTION POOL RUNNING? {}", isRunning());
			} catch (SQLException e) {
			}
			tries--;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		// Try to bootstrap connection pool and not return bad connections.
		bootstrapConnectionPool();
		return super.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// Hikari doesn't support new auth connections (called by Wildfly/GlassFish)

		if (username != null && username.contains("/")) {
			// Username appears as username/password
			String[] credentials = username.split("/");
			username = evaluateExpression("username", credentials[0]);
			password = evaluateExpression("password", credentials.length > 1 ? credentials[1] : "");
		} else {
			username = evaluateExpression("username", username);
			password = evaluateExpression("password", password);
		}
		
		if (Objects.equals(username, getUsername()) && Objects.equals(password, getPassword())) {
			// Allow it if same from initial config
			return getConnection();
		}
		// This will likely fail
		return super.getConnection(username, password);
	}

	// ==========================================
	// Extra setters methods seen from App Servers
	// ==========================================

	public void setDatabaseName(String databaseName) {
		// Open Liberty wants to invoke this method
	}
	
	public void setServerName(String serverName) {
		// Open Liberty wants to invoke this method
	}

	public void setUser(String user) {
		// Open Liberty wants to invoke this method
		setUsername(user);
	}
	
	public void setURL(String url) {
		// Open Liberty wants to invoke this method
		setJdbcUrl(url);
	}

	public void setCreate(String create) {
		// GlassFish wants to invoke this method
	}
}
