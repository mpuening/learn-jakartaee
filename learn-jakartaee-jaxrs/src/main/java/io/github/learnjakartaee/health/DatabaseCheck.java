package io.github.learnjakartaee.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class DatabaseCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE Database Check";
		
		// TODO Move to EJB

		boolean dataSourceFound = true; //dataSource != null;
		boolean isUp = true; //false;
		/*
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
		*/
		return HealthCheckResponse.named(name).withData("dataSource", dataSourceFound).status(isUp).build();
	}
}
