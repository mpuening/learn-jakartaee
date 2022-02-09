package io.github.learnjakartaee.service;

import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("ping")
public class PingService {

	@Inject
	@ConfigProperty(name = "greeting", defaultValue = "UNKNOWN")
	String greeting;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object ping() {
		Map<String, Object> response = Map.of("greeting", greeting);
		return response;
	}

}