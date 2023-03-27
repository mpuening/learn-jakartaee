package io.github.learnjakartaee;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Learn Jakarta EE JAX-RS", version = "1.0.0"))
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

}