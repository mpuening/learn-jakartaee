package io.github.learnjakartaee.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class SystemMemoryCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		String name = "Learn Jakarta EE Memory Usage Check";
		MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
		long memoryUsed =bean.getHeapMemoryUsage().getUsed();
		long memoryMax = bean.getHeapMemoryUsage().getMax();
		boolean isUp = memoryUsed < memoryMax * 0.9;
		long percent = (memoryUsed * 100) / memoryMax;
		return HealthCheckResponse.named(name).withData("percent", percent).status(isUp).build();
	}
}
