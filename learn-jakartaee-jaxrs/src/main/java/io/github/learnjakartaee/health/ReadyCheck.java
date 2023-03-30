package io.github.learnjakartaee.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import io.github.learnjakartaee.flyway.FlywayMigration;
import jakarta.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ReadyCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE Ready Check";
		boolean flywayComplete = FlywayMigration.isFlywayMigrationComplete();
		boolean isUp = flywayComplete;
		return HealthCheckResponse.named(name).withData("flyway", flywayComplete).status(isUp).build();
	}
}
