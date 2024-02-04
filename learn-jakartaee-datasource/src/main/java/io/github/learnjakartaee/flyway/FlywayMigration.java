package io.github.learnjakartaee.flyway;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

public class FlywayMigration {

	private static boolean flywayMigrationSkipped = false;

	private static boolean flywayMigrationComplete = false;

	public static boolean isFlywayMigrationSkipped() {
		return flywayMigrationSkipped;
	}

	public static void setFlywayMigrationSkipped() {
		flywayMigrationSkipped = true;
	}

	public static boolean isFlywayMigrationComplete() {
		return flywayMigrationComplete;
	}

	/**
	 * Utility method to run Flyway Migration once. Note this isn't thread safe
	 * or even safe across a cluster. This is just for demonstration purposes.
	 */
	public static void run(DataSource dataSource, ClassLoader loader) {
		try {
			if (dataSource != null && !flywayMigrationComplete) {
				Flyway flyway = Flyway
						.configure(loader)
						.dataSource(dataSource)
						.connectRetries(5)
						.baselineOnMigrate(true)
						.load();
				MigrateResult result = flyway.migrate();
				flywayMigrationComplete = result.success;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
