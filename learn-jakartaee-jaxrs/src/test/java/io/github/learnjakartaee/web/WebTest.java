package io.github.learnjakartaee.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.BaseTestCase;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
public class WebTest extends BaseTestCase {

	@Deployment
	public static WebArchive createTestDeployment() {
		return commonTestDeployment();
	}

	@Test
	public void testIndex() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "").toExternalForm());
		assertPageResponse(target);
	}

	@Test
	public void testIndexHtml() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "index.html").toExternalForm());
		assertPageResponse(target);
	}

	private void assertPageResponse(WebTarget target) {
		try (final Response response = target.request().get()) {
			assertEquals(200, response.getStatus());
			String body = response.readEntity(String.class);
			assertTrue(body.contains("<html>"));
		}
	}
}
