package io.github.learnjakartaee.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.github.learnjakartaee.ws.AircraftInterface;

public class AircraftClientTest {

	@Test
	public void testAircraftClent() {
		String test = "http://localhost:8080/learn-jakartaee-jaxws-server/AircraftService";
		AircraftInterface aircraftInterface = AircraftClient.createInterface(test, "admin", "password");
		assertNotNull(aircraftInterface);
		//String pong = aircraftInterface.ping("ping");
		//assertEquals("ping", pong);
		//System.out.println(pong);
	}
}
