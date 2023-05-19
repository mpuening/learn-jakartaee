package io.github.learnjakartaee.ping;

import java.util.Base64;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient
@ClientHeaderParam(name = "Authorization", value = "{basicAuth}")
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PingTestClient {

	@GET
	@Path("/api/ping")
	Response ping();

	default String basicAuth() {
		return "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes());
	}

}
