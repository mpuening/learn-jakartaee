package io.github.learnjakartaee.aircraft;

import java.util.Base64;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient
@ClientHeaderParam(name = "Authorization", value = "{basicAuth}")
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AircraftTestClient {

	@GET
	@Path("/api/aircraft")
	Response getAircraft();

	@POST
	@Path("/api/aircraft")
	Response createAircraft(Aircraft aircraft);

	@PUT
	@Path("/api/aircraft")
	Response updateAircraft(Aircraft aircraft);

	@DELETE
	@Path("/api/aircraft/{id}")
	Response deleteAircraft(@PathParam("id") String id);

	default String basicAuth() {
		return "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes());
	}

}
