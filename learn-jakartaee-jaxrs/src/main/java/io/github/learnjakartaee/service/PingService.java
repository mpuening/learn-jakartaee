package io.github.learnjakartaee.service;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("ping")
public class PingService {

	@Inject
	@ConfigProperty(name = "greeting")
	String greeting;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object ping() {
		Map<String, Object> response = Map.of("greeting", greeting);
		return response;
	}

}