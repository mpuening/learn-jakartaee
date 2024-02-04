package org.example.sql.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.github.learnjakartaee.flyway.FlywayMigration;

public class DataSourceAsserts {

	public static void assertDataSource(DataSource dataSource) throws SQLException {
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
