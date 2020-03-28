package io.github.learnjakartaee.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Stateless;

import io.github.learnjakartaee.model.Person;

@Stateless
public class PeopleService {

	/**
	 * This is cheating, as we aren't really stateless are we?
	 *
	 * I just didn't want to add in the database.
	 */
	private static Map<String, Person> people = new ConcurrentHashMap<>();

	public Collection<Person> getPeople() {
		return people.values();
	}

	public Person addPerson(String firstName, String lastName) throws PeopleException {
		Person newPerson = new Person();
		newPerson.setId(UUID.randomUUID());
		newPerson.setFirstName(firstName);
		newPerson.setLastName(lastName);
		String key = newPerson.getLastName() + "," + newPerson.getFirstName();
		if (people.containsKey(key)) {
			throw new PeopleException(String.format("%s is already in the system.", key));
		}
		people.put(key, newPerson);
		return newPerson;
	}
}
