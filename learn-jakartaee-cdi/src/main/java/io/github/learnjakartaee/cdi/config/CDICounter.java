package io.github.learnjakartaee.cdi.config;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.inject.Qualifier;

/**
 * Just a simple bean. This class also defines three qualifiers
 * to indicate different types of counters.
 */
public class CDICounter implements Serializable {

	private static final long serialVersionUID = 1198422944735224252L;

	private final AtomicInteger count = new AtomicInteger();

	public int incrementAndGet() {
		return count.incrementAndGet();
	}
	
	@Qualifier
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
	public static @interface AppScopedCounter {
	}

	@Qualifier
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
	public static @interface RequestScopedCounter {
	}

	@Qualifier
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
	public static @interface SessionScopedCounter {
	}

}
