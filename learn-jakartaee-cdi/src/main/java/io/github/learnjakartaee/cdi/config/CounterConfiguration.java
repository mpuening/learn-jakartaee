package io.github.learnjakartaee.cdi.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class CounterConfiguration {

	@Produces
	@CDICounter.AppScopedCounter
	public CDICounter cdiCounter() {
		return new CDICounter();
	}

	@Produces
	@RequestScoped
	@CDICounter.RequestScopedCounter
	public CDICounter requestScopedCDICounter() {
		return new CDICounter();
	}

	@Produces
	@SessionScoped
	@CDICounter.SessionScopedCounter
	public CDICounter sessionScopedCDICounter() {
		return new CDICounter();
	}
}
