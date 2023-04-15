package io.github.learnjakartaee.aircraft.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.test.EjbJarBuilder;
import jakarta.ejb.EJB;

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
	public void testAircraftService() {
		assertNotNull(aircraftService);
	}


}
