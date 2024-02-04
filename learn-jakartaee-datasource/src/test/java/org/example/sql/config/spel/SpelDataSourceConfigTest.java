package org.example.sql.config.spel;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.example.sql.config.DataSourceAsserts;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.sql.AppDataSource;
import io.github.learnjakartaee.test.WebAppWarBuilder;
import jakarta.inject.Inject;

@ExtendWith(ArquillianExtension.class)
public class SpelDataSourceConfigTest {

	@Deployment(testable = true)
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "spel");

		return new WebAppWarBuilder("learn-jakartaee-datasource")
				.packages("io.github.learnjakartaee.sql", "org.example.sql.config.spel")
				.build();
	}

	@Inject
	@AppDataSource
	DataSource dataSource;

	@Test
	public void testDataSourceConfig() throws SQLException {
		DataSourceAsserts.assertDataSource(dataSource);
	}
}
