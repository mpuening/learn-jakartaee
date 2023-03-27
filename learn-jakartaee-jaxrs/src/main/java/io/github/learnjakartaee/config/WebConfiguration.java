package io.github.learnjakartaee.config;

import java.io.InputStream;

import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Provide a simple page to help users find the right links for each app server
 */
@Path("")
public class WebConfiguration {

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public Response slash() {
		return indexHtml();
	}
	
	@Path("index.html")
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public Response indexHtml() {
		InputStream indexHtml = this.getClass().getResourceAsStream("/index.html");
		return Response.ok().entity(indexHtml).build();
	}
}
