package io.github.learnjakartaee.service;

import static com.jayway.jsonassert.JsonAssert.with;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.BaseTestCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
public class PingServiceTest extends BaseTestCase {

	@Deployment
	public static WebArchive createTestDeployment() {
		return commonTestDeployment();
	}

	@Inject
	PingService pingService;

	@Test
	public void testPingService() {
		assertNotNull(pingService);

		Response response = pingService.ping();
		assertEquals(200, response.getStatus());
		assertEquals("{greeting=HelloTest}", response.getEntity().toString());
	}

	@Test
	public void testPingServiceClient() {
		assertNotNull(apiTestClient);

		Response response = apiTestClient.ping();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class)).assertThat("$.greeting", is("HelloTest"));
	}

	@Test
	public void testPingServiceUsingRestEasy() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "api/ping").toExternalForm());

		try (final Response response = target.request().accept(MediaType.APPLICATION_JSON).get()) {
			assertEquals(200, response.getStatus());
			with(response.readEntity(String.class)).assertThat("$.greeting", is("HelloTest"));
		}
	}
}
