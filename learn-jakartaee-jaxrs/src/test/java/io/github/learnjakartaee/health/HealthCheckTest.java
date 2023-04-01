package io.github.learnjakartaee.health;

import static com.jayway.jsonassert.JsonAssert.collectionWithSize;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.BaseTestCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * When running within Eclipse/IntelliJ, make sure to add VM args:
 *
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * --add-opens=java.base/java.io=ALL-UNNAMED
 * --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED
 *
 * In Eclipse, the JRE configuration had a field for default VM args, so you
 * need only to set it once and apply it for all test cases.
 */
@ExtendWith(ArquillianExtension.class)
public class HealthCheckTest extends BaseTestCase {

	@Deployment
	public static WebArchive createTestDeployment() {
		return commonTestDeployment();
	}

	@Inject
	@RestClient
	// See microprofile-config.properties for url
	protected HealthCheckTestClient healthCheckTestClient;

	@Test
	public void testHealthStarted() {
		assertNotNull(healthCheckTestClient);

		Response response = healthCheckTestClient.healthStarted();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.status", is("UP"))
				.and()
				.assertThat("$.checks[0].name", is("Learn Jakarta EE Startup Check"))
				.and()
				.assertThat("$.checks[0].status", is("UP"));
	}

	@Test
	public void testHealthReady() {
		assertNotNull(healthCheckTestClient);

		Response response = healthCheckTestClient.healthReady();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.status", is("UP"))
				.and()
				.assertThat("$.checks[0].name", is("Learn Jakarta EE Ready Check"))
				.and()
				.assertThat("$.checks[0].status", is("UP"));
	}

	@Test
	public void testHealthLive() {
		assertNotNull(healthCheckTestClient);

		Response response = healthCheckTestClient.healthLive();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				// Super busy CI runners have too little resources, may cause DOWN
				.assertThat("$.status", anyOf(is("UP"), is("DOWN")))
				.and()
				.assertThat("$.checks", is(collectionWithSize(equalTo(3))))
				.and()
				// Order of checks is random, but they all start the same
				.assertThat("$.checks[0].name", startsWith("Learn Jakarta EE"))
				.and()
				.assertThat("$.checks[0].status", anyOf(is("UP"), is("DOWN")));
	}
}
