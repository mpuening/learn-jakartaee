package io.github.learnjakartaee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.WebAppWarBuilder;
import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.AircraftService;
import jakarta.xml.ws.BindingProvider;

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
public class AircraftServiceTest {

	@Deployment
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unittest");

		return new WebAppWarBuilder("learn-jakartaee-jaxws.war")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.webXml()
				.mavenDependencies()
				.build();
	}

	@ArquillianResource
	private URL baseURL;

	@Test
	public void testAircraftService() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);
		String message = "Hello";
		String response = aircraftInterface.ping(message);
		assertEquals(message, response);
	}

	public static AircraftInterface createInterface(String endpoint) {
		URL wsdlLocation = AircraftService.class.getResource("/META-INF/services/service.wsdl");
		AircraftService aircraftService = new AircraftService(wsdlLocation,
				new QName("https://learnjaxws.github.io/learn/webservice/soap", "AircraftService"));
		AircraftInterface aircraftInterface = aircraftService.getAircraftInterfaceBinding();

		// Basic HTTP Authentication
		String username = "admin";
		String password = "password";
		Map<String, Object> requestContext = ((BindingProvider) aircraftInterface).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		return aircraftInterface;
	}
}
