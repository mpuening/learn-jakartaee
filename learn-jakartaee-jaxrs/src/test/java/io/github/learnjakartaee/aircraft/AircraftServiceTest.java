package io.github.learnjakartaee.aircraft;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.BaseTestCase;
import io.github.learnjakartaee.aircraft.model.Aircraft;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class AircraftServiceTest extends BaseTestCase {

	@Deployment
	public static WebArchive createTestDeployment() {
		return commonTestDeployment();
	}

	@Inject
	@RestClient
	// See microprofile-config.properties for url
	protected AircraftTestClient aircraftTestClient;

	@Order(1)
	@Test
	public void testGetAircraft() {
		assertNotNull(aircraftTestClient);

		Response response = aircraftTestClient.getAircraft();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.data[0].id", is("6b19f137-73f2-49ec-88e5-f98a97914761"))
				.assertThat("$.data[0].designation", is("B-21"))
				.assertThat("$.data[0].name", is("Raider"))
				.assertThat("$.data[0].manufacturer", is("Northrop Grumman"));
	}

	@Order(2)
	@Test
	public void testCreateAircraft() {
		assertNotNull(aircraftTestClient);

		Aircraft aircraft = new Aircraft();
		aircraft.setDesignation("B-17");
		aircraft.setName("Flying Fortess");
		aircraft.setManufacturer("Boeing");
		aircraft.setFirstFlight(LocalDate.of(1935, 7, 28));
		aircraft.setProduced(12_731);

		Response response = aircraftTestClient.createAircraft(aircraft);
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				// .assertThat("$.data.id", is("6b19f137-73f2-49ec-88e5-f98a97914761"))
				.assertThat("$.data.designation", is("B-17"))
				.assertThat("$.data.name", is("Flying Fortess"))
				.assertThat("$.data.manufacturer", is("Boeing"))
				.assertThat("$.data.firstFlight", is("1935-07-28"))
				.assertThat("$.data.produced", is(12_731));
	}

	@Order(3)
	@Test
	public void testCreateBadAircraft() {
		assertNotNull(aircraftTestClient);

		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setName("Phantom");
			aircraftTestClient.createAircraft(aircraft);
			fail("Test case should have failed.");
		} catch (WebApplicationException ex) {
			Response response = ex.getResponse();
			assertEquals(400, response.getStatus());
			with(response.readEntity(String.class))
				.assertThat("$.title", is("Bad Request"))
				.assertThat("$.detail", is("Please provide a name and designation for the aircraft"));
		}
	}

	@Order(4)
	@Test
	public void testUpdateAircraft() {
		assertNotNull(aircraftTestClient);

		Aircraft aircraft = new Aircraft();
		aircraft.setId("6b19f137-73f2-49ec-88e5-f98a97914761");
		aircraft.setDesignation("B-21");
		aircraft.setName("Raider");
		aircraft.setManufacturer("Northrop Grumman");
		aircraft.setFirstFlight(LocalDate.of(2023, 11, 10));
		aircraft.setProduced(1);

		Response response = aircraftTestClient.updateAircraft(aircraft);
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.data.id", is("6b19f137-73f2-49ec-88e5-f98a97914761"))
				.assertThat("$.data.designation", is("B-21"))
				.assertThat("$.data.name", is("Raider"))
				.assertThat("$.data.manufacturer", is("Northrop Grumman"))
				.assertThat("$.data.firstFlight", is("2023-11-10"))
				.assertThat("$.data.produced", is(1));
	}

	@Order(5)
	@Test
	public void testUpdateBadAircraft() {
		assertNotNull(aircraftTestClient);

		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setId("bad-id");
			aircraft.setDesignation("B-21");
			aircraft.setName("Raider");
			aircraft.setManufacturer("Northrop Grumman");
			aircraft.setFirstFlight(LocalDate.of(2023, 11, 10));
			aircraft.setProduced(1);
			aircraftTestClient.updateAircraft(aircraft);
			fail("Test case should have failed.");
		} catch (WebApplicationException ex) {
			Response response = ex.getResponse();
			assertEquals(404, response.getStatus());
			with(response.readEntity(String.class))
				.assertThat("$.title", is("Not Found"))
				.assertThat("$.detail", is("Provided aircraft to update does not exist"));
		}
	}

	@Order(6)
	@Test
	public void testDeleteAircraft() {
		assertNotNull(aircraftTestClient);

		Response response = aircraftTestClient.deleteAircraft("6b19f137-73f2-49ec-88e5-f98a97914761");
		assertEquals(204, response.getStatus());
	}

	@Order(7)
	@Test
	public void testDeleteNonExistentAircraft() {
		assertNotNull(aircraftTestClient);

		try {
			aircraftTestClient.deleteAircraft("nonid");
			fail("Test case should have failed.");
		} catch (WebApplicationException ex) {
			Response response = ex.getResponse();
			assertEquals(404, response.getStatus());
			with(response.readEntity(String.class))
				.assertThat("$.title", is("Not Found"))
				.assertThat("$.detail", is("Provided aircraft to update does not exist"));

		}
	}
}
