package io.github.learnjakartaee.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Liveness
@ApplicationScoped
public class EjbCheck implements HealthCheck {

	@Inject @EJB
	protected io.github.learnjakartaee.aircraft.service.AircraftService aircraftService;

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE EJB Check";

		boolean ejbFound = aircraftService != null;
		boolean isUp = false;
		try {
			aircraftService.findById("no-id");
			isUp = true;
		} catch (Exception ignored) {
		}
		return HealthCheckResponse.named(name).withData("ejb", ejbFound).status(isUp).build();
	}
}
