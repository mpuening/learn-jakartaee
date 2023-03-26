package io.github.learnjakartaee.health;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class SystemCpuLoadCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE CPU Load Check";
		boolean isUp = true;
		long load = -1;
		try {
			OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
			if (bean instanceof com.sun.management.OperatingSystemMXBean) {
				double cpuLoad = ((com.sun.management.OperatingSystemMXBean) bean).getCpuLoad();
				isUp = cpuLoad < 0.95;
				load = (long)(cpuLoad * 100);
			}
		} catch (Throwable ignored) {
		}
		return HealthCheckResponse.named(name).withData("cpuLoad", load).status(isUp).build();
	}
}
