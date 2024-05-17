package io.github.learnjakartaee.ping;

import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RolesAllowed({ "user", "admin" })
@Path("api/ping")
@Named
@ApplicationScoped
public class PingService {

	@Inject
	@ConfigProperty(name = "greeting", defaultValue = "UNKNOWN")
	String greeting;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponseSchema(responseCode = "200", responseDescription = "Ping Response", value = PingResponse.class)
	public Response ping() {
		Map<String, Object> data = Map.of("greeting", greeting);
		return Response.status(Status.OK).entity(data).build();
	}

	@Schema
	public static class PingResponse {
		@Schema(properties = {
				@SchemaProperty(name = "greeting", type = SchemaType.STRING, description = "Hello message")
		})
		Object data;
	}
}