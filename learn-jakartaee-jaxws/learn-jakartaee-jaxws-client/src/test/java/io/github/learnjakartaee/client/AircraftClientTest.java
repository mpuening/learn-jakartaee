package io.github.learnjakartaee.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.github.learnjakartaee.ws.AircraftInterface;

public class AircraftClientTest {

	@Test
	public void testAircraftClent() {
		AircraftInterface aircraftInterface = AircraftClient.createInterface("http://localhost:8080/test");
		assertNotNull(aircraftInterface);
	}
}
