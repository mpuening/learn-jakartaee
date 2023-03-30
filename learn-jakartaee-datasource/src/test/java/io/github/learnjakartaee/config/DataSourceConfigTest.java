package io.github.learnjakartaee.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.flyway.FlywayMigration;
import io.github.learnjakartaee.sql.AppDataSource;
import io.github.learnjakartaee.test.WebAppWarBuilder;
import jakarta.inject.Inject;

@ExtendWith(ArquillianExtension.class)
public class DataSourceConfigTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {
		return new WebAppWarBuilder("learn-jakartaee-datasource")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@Inject
	@AppDataSource
	DataSource dataSource;

	@Test
	public void testDataSourceConfig() throws SQLException {
		assertNotNull(dataSource);
		assertTrue(FlywayMigration.isFlywayMigrationComplete());
		
		boolean isUp = false;
		String validationSql = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(validationSql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int value = resultSet.getInt(1);
				isUp = value == 1;
			}
		}
		assertTrue(isUp);
	}
}
