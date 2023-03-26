package io.github.learnjakartaee.health;

import org.apache.tomee.microprofile.health.MicroProfileHealthChecksEndpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;

/**
 * I don't understand why I need this class. Arquillian is not picking up
 * the @ApplicationScoped bean that this class extends from. I want the /health
 * end-points so the test cases work. It's possible that end-points are under a
 * different context, but I looked and didn't find one.
 */
@Path("api/health")
@ApplicationScoped
public class TomEEHealthEndPoint extends MicroProfileHealthChecksEndpoint {

}
