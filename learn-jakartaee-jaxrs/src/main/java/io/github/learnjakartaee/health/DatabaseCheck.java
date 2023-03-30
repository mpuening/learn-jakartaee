package io.github.learnjakartaee.health;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import io.github.learnjakartaee.sql.AppDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Liveness
@ApplicationScoped
public class DatabaseCheck implements HealthCheck {

	@Inject
	@AppDataSource
	DataSource dataSource;

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE Database Check";
		boolean dataSourceFound = dataSource != null;
		boolean isUp = false;
		if (dataSource != null) {
			String validationSql = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
			try (Connection connection = dataSource.getConnection();
					PreparedStatement statement = connection.prepareStatement(validationSql);
					ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int value = resultSet.getInt(1);
					isUp = value == 1;
				}
			} catch (SQLException ex) {
				// status will be DOWN
				ex.printStackTrace();
			}
		}
		return HealthCheckResponse.named(name).withData("dataSource", dataSourceFound).status(isUp).build();
	}
}
