package io.github.learnjakartaee.spring.config;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Just a simple bean.
 */
public class SpringCounter implements Serializable {

	private static final long serialVersionUID = -1186124803349489716L;

	private final AtomicInteger count = new AtomicInteger();

	public int incrementAndGet() {
		return count.incrementAndGet();
	}
}
