package io.github.learnjakartaee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.WebAppWarBuilder;
import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.AircraftService;
import io.github.learnjakartaee.ws.messages.AcknowledgeAircraftType;
import io.github.learnjakartaee.ws.messages.CreateAircraftType;
import io.github.learnjakartaee.ws.messages.DeleteAircraftType;
import io.github.learnjakartaee.ws.messages.GetAircraftType;
import io.github.learnjakartaee.ws.messages.ShowAircraftType;
import io.github.learnjakartaee.ws.messages.UpdateAircraftType;
import io.github.learnjakartaee.ws.schemas.AircraftType;
import io.github.learnjakartaee.ws.schemas.ManufacturerType;
import jakarta.xml.ws.BindingProvider;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(OrderAnnotation.class)
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

	@Order(0)
	@Test
	public void testAircraftServicePing() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		String message = "Hello";
		String response = aircraftInterface.ping(message);
		assertEquals(message, response);
	}

	@Order(1)
	@Test
	public void testAircraftServiceGetAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		GetAircraftType request = new GetAircraftType();
		ShowAircraftType response = aircraftInterface.getAircraft(request);
		assertNotNull(response.getAircrafts());
		assertEquals(1, response.getAircrafts().size());
		assertEquals("6b19f137-73f2-49ec-88e5-f98a97914761", response.getAircrafts().get(0).getId());
		assertEquals("B-21", response.getAircrafts().get(0).getDesignation());
		assertEquals("Raider", response.getAircrafts().get(0).getName());
		assertEquals(null, response.getAircrafts().get(0).getNickname());
		assertEquals("Northrop Grumman", response.getAircrafts().get(0).getManufacturer().getName());
		assertEquals(0, response.getAircrafts().get(0).getProduced());
	}

	@Order(2)
	@Test
	public void testAircraftServiceCreateAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setDesignation("B-17");
		aircraft.setName("Flying Fortress");

		CreateAircraftType request = new CreateAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.createAircraft(request);
		assertEquals(200, response.getCode());
		assertEquals("OK", response.getStatus());
		assertNotNull(response.getId());
	}

	@Order(3)
	@Test
	public void testAircraftServiceCreateBadAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setDesignation("B-17");
		ManufacturerType manufacturer = new ManufacturerType();
		manufacturer.setName("Boeing");
		aircraft.setManufacturer(manufacturer);

		CreateAircraftType request = new CreateAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.createAircraft(request);
		assertEquals(400, response.getCode());
		assertEquals("Bad Request", response.getStatus());
		assertEquals("Please provide a name and designation for the aircraft", response.getMessage());
		assertNull(response.getId());
	}

	@Order(4)
	@Test
	public void testAircraftServiceUpdateAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setId("6b19f137-73f2-49ec-88e5-f98a97914761");
		aircraft.setDesignation("B-21");
		aircraft.setName("Raider");
		aircraft.setProduced(1);
		ManufacturerType manufacturer = new ManufacturerType();
		manufacturer.setName("Northrop Grumman");
		aircraft.setManufacturer(manufacturer);

		UpdateAircraftType request = new UpdateAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.updateAircraft(request);
		assertEquals(200, response.getCode());
		assertEquals("OK", response.getStatus());
		assertEquals("6b19f137-73f2-49ec-88e5-f98a97914761", response.getId());
	}

	@Order(5)
	@Test
	public void testAircraftServiceUpdateBadAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setDesignation("B-17");
		ManufacturerType manufacturer = new ManufacturerType();
		manufacturer.setName("Boeing");
		aircraft.setManufacturer(manufacturer);

		UpdateAircraftType request = new UpdateAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.updateAircraft(request);
		assertEquals(400, response.getCode());
		assertEquals("Bad Request", response.getStatus());
		assertEquals("Please provide an existing aircraft to update", response.getMessage());
		assertNull(response.getId());
	}

	@Order(6)
	@Test
	public void testAircraftServiceDeleteAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setId("6b19f137-73f2-49ec-88e5-f98a97914761");

		DeleteAircraftType request = new DeleteAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.deleteAircraft(request);
		assertEquals(200, response.getCode());
		assertEquals("OK", response.getStatus());
		assertEquals("6b19f137-73f2-49ec-88e5-f98a97914761", response.getId());
	}

	@Order(7)
	@Test
	public void testAircraftServiceDeleteBadAircraft() {
		String endpoint = baseURL.toString() + "AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint);		

		AircraftType aircraft = new AircraftType();
		aircraft.setId("no-id");

		DeleteAircraftType request = new DeleteAircraftType();
		request.setAircraft(aircraft);
		AcknowledgeAircraftType response = aircraftInterface.deleteAircraft(request);
		assertEquals(400, response.getCode());
		assertEquals("Bad Request", response.getStatus());
		assertEquals("Expected to delete one record, got 0", response.getMessage());
		assertNull(response.getId());
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
