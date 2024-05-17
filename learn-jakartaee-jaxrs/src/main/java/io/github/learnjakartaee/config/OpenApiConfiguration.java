package io.github.learnjakartaee.config;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.models.media.Content;
import org.eclipse.microprofile.openapi.models.media.MediaType;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;
import org.microprofileext.openapi.swaggerui.StaticResourcesService;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@OpenAPIDefinition(
		info = @Info(title = "Learn Jakarta EE JAX-RS", version = "1.0.0"), 
		security = { @SecurityRequirement(name = "basicAuth") })
@SecuritySchemes(value = {
		@SecurityScheme(securitySchemeName = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
})
@PermitAll
@Path("/openapi-ui/")
public class OpenApiConfiguration implements OASFilter {

	/**
	 * Security configuration.
	 */
	@Override
	public org.eclipse.microprofile.openapi.models.security.SecurityScheme filterSecurityScheme(
			org.eclipse.microprofile.openapi.models.security.SecurityScheme securityScheme) {
		return securityScheme;
	}
	
	/**
	 * Add boilerplate for common 4xx and 5xx error responses
	 */
	@Override
	public org.eclipse.microprofile.openapi.models.Operation filterOperation(
			org.eclipse.microprofile.openapi.models.Operation operation) {

		addAPIResponse(operation, Status.UNAUTHORIZED);
		addAPIResponse(operation, Status.FORBIDDEN);

		return operation;
	}

	protected void addAPIResponse(org.eclipse.microprofile.openapi.models.Operation operation, Status status) {
		APIResponse response = OASFactory.createObject(APIResponse.class)
				.description(status.getReasonPhrase())
				.content(OASFactory.createObject(Content.class)
						.addMediaType("application/json", OASFactory.createObject(MediaType.class)
								.schema(OASFactory.createObject(Schema.class).ref("#/components/schemas/ProblemDetail"))));
		operation.getResponses().addAPIResponse(String.valueOf(status.getStatusCode()), response);
	}

	private static final String SWAGGER_UI_VERSION = "4.14.2";
	private static final String OAUTH2_REDIRECT_HTML = "oauth2-redirect.html";

	/**
	 * QUESTIONABLE
	 *
	 * What am I missing? Why can't I find any code that serves up the swagger
	 * oauth2 redirect page from its default location?
	 */
	@GET
	@Produces(jakarta.ws.rs.core.MediaType.TEXT_HTML)
	@Path(OAUTH2_REDIRECT_HTML)
	@Operation(hidden = true)
	public Response getOAuth2RedirectHtml() {
		StaticResourcesService service = new StaticResourcesService();
		return service
				.staticJsResources(String.format("webjars/swagger-ui/%s/%s", SWAGGER_UI_VERSION, OAUTH2_REDIRECT_HTML));
	}
}
