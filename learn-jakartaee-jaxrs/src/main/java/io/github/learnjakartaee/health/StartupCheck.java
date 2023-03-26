package io.github.learnjakartaee.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class StartupCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE Startup Check";
		// If you got here, you're up
		return HealthCheckResponse.named(name).up().build();
	}
}
