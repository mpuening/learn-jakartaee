package io.github.learnjakartaee;

import java.net.URL;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import io.github.learnjakartaee.test.RestApiWarBuilder;
import jakarta.inject.Inject;

public abstract class BaseTestCase {

	/**
	 * Arquillian doesn't seem to support re-using deployments, so the
	 * actual @Deployment must exist in the leaf classes
	 */
	public static WebArchive commonTestDeployment() {
		return new RestApiWarBuilder("learn-jakartaee-jaxrs.war")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.persistenceXml()
				.build(true);
	}

	@ArquillianResource
	protected URL baseURL;

	@Inject
	@RestClient
	// See microprofile-config.properties for url
	protected ApiTestClient apiTestClient;
}
