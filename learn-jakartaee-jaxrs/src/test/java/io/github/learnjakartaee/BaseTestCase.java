package io.github.learnjakartaee;

import java.net.URL;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.RestApiWarBuilder;

public abstract class BaseTestCase {

	/**
	 * Arquillian doesn't seem to support re-using deployments, so the
	 * actual @Deployment must exist in the leaf classes
	 */
	public static WebArchive commonTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unittest");

		return new RestApiWarBuilder("learn-jakartaee-jaxrs.war")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.publicIndexHtml()
				.build();
	}

	@ArquillianResource
	protected URL baseURL;
}
