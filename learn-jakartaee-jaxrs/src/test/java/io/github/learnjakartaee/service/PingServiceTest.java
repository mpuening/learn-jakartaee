package io.github.learnjakartaee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.test.RestApiWarBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
public class PingServiceTest {

	@Deployment
	public static WebArchive createTestDeployment() {
		return new RestApiWarBuilder("learn-jakartaee-jaxrs.war")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.build();
	}

	@ArquillianResource
	private URL baseURL;

	@Inject
	PingService pingService;

    @Inject
    @RestClient
    // See microprofile-config.properties for url
    private PingServiceClient pingServiceClient;

	@Test
	public void testPingService() {
		assertNotNull(pingService);
		
		String response = pingService.ping().toString();
		assertEquals("{greeting=Hello}", response);
	}
	
	@Test
	public void testPingServiceClient() {
		assertNotNull(pingServiceClient);

		Response response = pingServiceClient.ping();
		assertEquals(200, response.getStatus());
		assertEquals("{\"greeting\":\"Hello\"}", response.readEntity(String.class));
	}
	
	@Test
	public void testPingServiceUsingRestEasy() throws MalformedURLException {
		assertNotNull(baseURL);
		
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(new URL(baseURL, "api/ping").toExternalForm());

        try (final Response response = target
        		.request()
                .accept(MediaType.APPLICATION_JSON)
                .get()) {
    		assertEquals(200, response.getStatus());
    		assertEquals("{\"greeting\":\"Hello\"}", response.readEntity(String.class));
        }
	}
}
