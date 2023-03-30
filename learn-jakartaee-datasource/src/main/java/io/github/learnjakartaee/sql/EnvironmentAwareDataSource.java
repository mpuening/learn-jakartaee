package io.github.learnjakartaee.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import com.zaxxer.hikari.HikariDataSource;

/**
 * This is an Hikari DataSource extension that is capable of looking up database
 * connection information from environment variables or system properties.
 * Variable names are indicated by starting with '$'. Default values are
 * supported by having a value coming after a ":" that is after the variable
 * name. For example: $DB_USERNAME:username
 *
 * Much more could be added to this class such as supporting values from
 * Microprofile Config sources, JNDI names, or encryption. Also, not added to
 * this class are setters to externalize the connection pool settings, or having
 * a validation SQL statement. But all that should be fairly easy to add. I just
 * don't need it for this project.
 *
 * See the test DataSourceConfiguration class for example usage.
 */
public class EnvironmentAwareDataSource extends HikariDataSource {

	@Override
	public void setDriverClassName(String driverClassName) {
		super.setDriverClassName(lookupValue(driverClassName));
	}

	@Override
	public void setJdbcUrl(String jdbcUrl) {
		super.setJdbcUrl(lookupValue(jdbcUrl));
	}

	@Override
	public void setUsername(String username) {
		super.setUsername(lookupValue(username));
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(lookupValue(password));
	}

	protected String lookupValue(String value) {
		if (value != null && value.startsWith("$")) {
			String key = value.substring(1);
			String defaultValue = key;
			int hasDefault = value.indexOf(':');
			if (hasDefault >= 0) {
				key = value.substring(1, hasDefault);
				defaultValue = value.substring(hasDefault + 1);
			}
			String envValue = System.getenv(key);
			if (envValue != null) {
				value = envValue;
			} else {
				value = System.getProperty(key, defaultValue);
			}
		}
		return value;
	}

	// ==========================================
	// Bootstrap logic
	// ==========================================

	private static boolean isPoolStarted = false;
	private static int tries = 3;

	protected void bootstrapConnectionPool() {
		while (!isPoolStarted && tries > 0) {
			try (Connection connection = super.getConnection()) {
				connection.getMetaData();
				isPoolStarted = true;
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
		// Hikari doesn't support new auth connections (called by Wildfly)

		username = lookupValue(username);
		password = lookupValue(password);
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
