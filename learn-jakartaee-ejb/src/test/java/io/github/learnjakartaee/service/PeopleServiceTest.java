package io.github.learnjakartaee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.model.Person;
import io.github.learnjakartaee.test.EjbJarBuilder;
import jakarta.ejb.EJB;

@ExtendWith(ArquillianExtension.class)
public class PeopleServiceTest {

	@Deployment(testable = true)
	public static JavaArchive createTestDeployment() {
		return new EjbJarBuilder("learn-jakartaee-ejb")
				.packages("io.github.learnjakartaee")
				.build();
	}

	@EJB
	PeopleService peopleService;

	@Test
	public void testAddAndFindPerson() throws PeopleException {
		assertNotNull(peopleService);

		Person alice = peopleService.addPerson("Alice", "Alexander");
		assertNotNull(alice);
		assertNotNull(alice.getId());
		assertEquals("Alice", alice.getFirstName());
		assertEquals("Alexander", alice.getLastName());

		Person person = peopleService.getPerson(alice.getId().toString());
		assertNotNull(person);
		assertNotNull(person.getId());
		assertEquals("Alice", person.getFirstName());
		assertEquals("Alexander", person.getLastName());
	}

	@Test
	public void testPersonNotFound() throws PeopleException {
		assertNotNull(peopleService);
		Assertions.assertThrows(PeopleException.class, () -> {
			peopleService.getPerson("4687902f-a4e9-4fe6-9224-2646925c840a");
		});
	}
}
