package io.github.learnjakartaee.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

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

	protected void warmConnectionPool() {
		while (!isPoolStarted && tries > 0) {
			try (Connection connection = super.getConnection()) {
				connection.getMetaData();
				isPoolStarted = true;
			} catch (SQLException e) {
			}
			tries--;
		}
	}

	/**
	 * Try to bootstrap connection pool and not return bad connections.
	 */
	@Override
	public Connection getConnection() throws SQLException {
		warmConnectionPool();
		return super.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// Hikari doesn't support new auth connections (called by Wildfly)

		// username = lookupValue(username);
		// password = lookupValue(password);
		return getConnection();
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
		// Glassfish wants to invoke this method
	}
}
