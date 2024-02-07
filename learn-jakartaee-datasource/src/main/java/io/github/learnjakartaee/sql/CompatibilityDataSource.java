package io.github.learnjakartaee.sql;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * This is an Hikari DataSource extension that is responsible for smoothing
 * over the differences between application servers. The intent is to have
 * a single class and configuration that would work on Open Liberty,
 * GlassFish, WildFly and Apache TomEE. If you peruse the code below, you
 * will see what this class has to deal with.
 *
 * Not (yet) added to this class are setters to externalize the connection pool
 * settings, or having a validation SQL statement. But all that should be fairly
 * easy to add.
 *
 * This class that be extended to process the value in the configuration.
 *
 * See the DataSourceConfiguration class for usage.
 *
 * QUESTIONABLE Does this place a connection pool on top of a connection pool?
 */
public class CompatibilityDataSource extends HikariDataSource {

	protected final Logger LOG = LoggerFactory.getLogger(CompatibilityDataSource.class);

	public CompatibilityDataSource() {
		this.setMinimumIdle(3);
	}

	@Override
	public void setDriverClassName(String driverClassName) {
		if (driverClassName != null) {
			super.setDriverClassName(evaluateValue("driver", driverClassName));
		}
	}

	/**
	 * WildFly requires an encoded URL in the source code. Otherwise, URL parameters
	 * get truncated.
	 */
	@Override
	public void setJdbcUrl(String jdbcUrl) {
		if (jdbcUrl != null) {
			jdbcUrl = evaluateValue("url", jdbcUrl);
			try {
				jdbcUrl = URLDecoder.decode(jdbcUrl, StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			super.setJdbcUrl(jdbcUrl);
		}
	}

	/**
	 * For GlassFish support, the username may appear as a delimited
	 * username/password string. GlassFish does not invoke the setPassword setter
	 * but instead calls getConnection(u,p) which is not supported by Hikari.
	 * Doubling up the username as a username/password pair allows us to check the
	 * username and password in the getConnection(u,p) method against the configured
	 * credentials.
	 *
	 * This causes a limitation in that '/' is a reserved character.
	 */
	@Override
	public void setUsername(String username) {
		if (username != null && username.contains("/")) {
			// Username appears as username/password pair
			String[] credentials = username.split("/");
			super.setUsername(evaluateValue("username1/2", credentials[0]));
			super.setPassword(evaluateValue("password2/2", credentials.length > 1 ? credentials[1] : ""));
		} else if (username != null) {
			super.setUsername(evaluateValue("username", username));
		}
	}

	@Override
	public void setPassword(String password) {
		if (password != null) {
			super.setPassword(evaluateValue("password", password));
		}
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
			username = evaluateValue("username", credentials[0]);
			password = evaluateValue("password", credentials.length > 1 ? credentials[1] : "");
		} else {
			username = evaluateValue("username", username);
			password = evaluateValue("password", password);
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

	/**
	 * Sub-classes are responsible for the implementation
	 *
	 * @param description Human-readable description of value
	 * @param value       Value to evaluate
	 * @return
	 */
	protected String evaluateValue(String description, String value) {
		return value;
	}
}
