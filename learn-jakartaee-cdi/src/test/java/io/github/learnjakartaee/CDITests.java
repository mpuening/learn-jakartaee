package io.github.learnjakartaee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.test.WebAppWarBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
public class CDITests {

	@Deployment
	public static WebArchive commonTestDeployment() {
		return new WebAppWarBuilder("learn-jakartaee-cdi.war")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.webXml()
				.persistenceXml()
				.htmls()
				.mavenDependencies()
				.build();
	}

	@ArquillianResource
	protected URL baseURL;

	@Test
	public void testIndexHtml() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "index.html").toExternalForm());
		try (final Response response = target.request().get()) {
			assertEquals(200, response.getStatus());
			String body = response.readEntity(String.class);
			assertTrue(body.contains("<html>"));
		}
	}

	@Test
	public void testGetPostsViaCDI() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "cdi/posts").toExternalForm());
		try (final Response response = target.request().get()) {
			assertEquals(200, response.getStatus());
			String body = response.readEntity(String.class);
			assertTrue(body.contains("Read"));
		}
	}

	@Test
	public void testGetPostsViaSpring() throws MalformedURLException {
		assertNotNull(baseURL);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(new URL(baseURL, "spring/posts").toExternalForm());
		try (final Response response = target.request().get()) {
			assertEquals(200, response.getStatus());
			String body = response.readEntity(String.class);
			assertTrue(body.contains("Read"));
		}
	}
}
