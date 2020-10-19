package io.github.learnjakartaee.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.ejb.Stateless;

import io.github.learnjakartaee.model.Person;

@Stateless
public class PeopleService {

	/**
	 * This is cheating, as we aren't really stateless are we?
	 *
	 * I just didn't want to add in the database, so we have this cheap
	 * implementation.
	 */
	private static Map<String, Person> people = new ConcurrentHashMap<>();

	private static Map<String, Person> names = new ConcurrentHashMap<>();

	public Collection<Person> getPeople() {
		return people.values();
	}

	public Person getPerson(String id) throws PeopleException {
		if (!people.containsKey(id)) {
			throw new PeopleException(String.format("%s is not in the system.", id));
		}
		return people.get(id);
	}

	public Person addPerson(String firstName, String lastName) throws PeopleException {
		Person newPerson = new Person();
		newPerson.setId(UUID.randomUUID());
		newPerson.setFirstName(firstName);
		newPerson.setLastName(lastName);
		String key = newPerson.getUniqueKey();
		if (names.containsKey(key)) {
			throw new PeopleException(String.format("%s is already in the system.", key));
		}
		people.put(newPerson.getId().toString(), newPerson);
		names.put(key, newPerson);
		return newPerson;
	}

	public Person updatePerson(Person updatedPerson) throws PeopleException {
		if (!people.containsKey(updatedPerson.getId().toString())) {
			throw new PeopleException(String.format("%s is not in the system.", updatedPerson.getId().toString()));
		}
		Person previousPerson = people.get(updatedPerson.getId().toString());
		people.remove(previousPerson.getId().toString());
		names.remove(previousPerson.getUniqueKey());
		people.put(updatedPerson.getId().toString(), updatedPerson);
		names.put(updatedPerson.getUniqueKey(), updatedPerson);
		return updatedPerson;
	}

	public void deletePerson(String id) throws PeopleException {
		if (!people.containsKey(id)) {
			throw new PeopleException(String.format("%s is not in the system.", id));
		}
		Person deletedPerson = people.get(id);
		people.remove(deletedPerson.getId().toString());
		names.remove(deletedPerson.getUniqueKey());
	}
}
