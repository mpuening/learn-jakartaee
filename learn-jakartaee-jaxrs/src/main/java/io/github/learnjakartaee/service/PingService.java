package io.github.learnjakartaee.service;

import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("ping")
public class PingService {

	@Inject
	@ConfigProperty(name = "greeting", defaultValue = "UNKNOWN")
	String greeting;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping() {
		Map<String, Object> data = Map.of("greeting", greeting);
		return Response.status(Status.OK).entity(data).build();
	}

}