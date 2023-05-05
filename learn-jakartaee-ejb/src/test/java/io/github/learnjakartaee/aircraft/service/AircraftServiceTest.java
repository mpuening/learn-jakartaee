package io.github.learnjakartaee.aircraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import io.github.learnjakartaee.test.EjbJarBuilder;
import jakarta.ejb.EJB;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
public class AircraftServiceTest {

	@Deployment(testable = true)
	public static JavaArchive createTestDeployment() {
		return new EjbJarBuilder("learn-jakartaee-ejb.jar")
				.packages("io.github.learnjakartaee")
				.beansXml()
				.persistenceXml()
				.build();
	}

	@EJB
	AircraftService aircraftService;
	
	@Test
	@Order(1)
	public void testAircraftServiceFindAll() {
		assertNotNull(aircraftService);
		List<Aircraft> aircraft = aircraftService.findAll();
		assertNotNull(aircraft);
		assertEquals(1, aircraft.size());
		assertEquals("6b19f137-73f2-49ec-88e5-f98a97914761", aircraft.get(0).getId());
		assertEquals("B-21", aircraft.get(0).getDesignation());
		assertEquals("Raider", aircraft.get(0).getName());
		assertEquals(null, aircraft.get(0).getNickname());
		assertEquals("Northrop Grumman", aircraft.get(0).getManufacturer());
		assertEquals(0 , aircraft.get(0).getProduced());
		assertEquals(null, aircraft.get(0).getFirstFlight());
	}

	@Test
	@Order(2)
	public void testAircraftServiceFindByDesignation() {
		assertNotNull(aircraftService);
		Aircraft b21 = aircraftService.findByDesignation("B-21").get();
		assertNotNull(b21);
		assertEquals("6b19f137-73f2-49ec-88e5-f98a97914761", b21.getId());
		assertEquals("B-21", b21.getDesignation());
		assertEquals("Raider", b21.getName());
		assertEquals(null, b21.getNickname());
		assertEquals("Northrop Grumman", b21.getManufacturer());
		assertEquals(0 , b21.getProduced());
		assertEquals(null, b21.getFirstFlight());
	}

	@Test
	@Order(3)
	public void testAircraftServiceCreate() throws AppException {
		assertNotNull(aircraftService);

		Aircraft f16 = new Aircraft();
		f16.setDesignation("F-16");
		f16.setName("Fighting Falcon");
		f16.setNickname("Viper");
		f16.setManufacturer("General Dynamics");
		f16.setProduced(4604);
		f16.setFirstFlight(LocalDate.of(1974, 1, 20));
		
		f16 = aircraftService.createNewAircraft(f16);
		assertNotNull(f16);
		assertNotNull(f16.getId());
	}

	@Test
	@Order(4)
	public void testAircraftServiceUpdate() throws AppException {
		assertNotNull(aircraftService);
		
		Aircraft b21 = aircraftService.findByDesignation("B-21").get();
		assertNotNull(b21);
		b21.setProduced(1);
		b21 = aircraftService.updateExistingAircraft(b21);
		assertEquals(1, b21.getProduced());
	}

	@Test
	@Order(5)
	public void testAircraftServiceDelete() throws AppException {
		assertNotNull(aircraftService);
		
		Aircraft b21 = aircraftService.findByDesignation("B-21").get();
		assertNotNull(b21);
		aircraftService.deleteExistingAircraft(b21);
		
		Optional<Aircraft> check = aircraftService.findByDesignation("B-21");
		assertEquals(false, check.isPresent());
	}
}
