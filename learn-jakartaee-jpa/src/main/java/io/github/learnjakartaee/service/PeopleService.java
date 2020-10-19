package io.github.learnjakartaee.service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import io.github.learnjakartaee.model.Person;

@Stateless
public class PeopleService {

	// See persistence.xml for name
	@PersistenceContext(name = "jdbc/personDataSource")
    private EntityManager entityManager;

	public List<Person> getPeople() {
		return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

	public Person addPerson(String name) {
		Person newPerson = new Person();
		newPerson.setName(name);
		entityManager.persist(newPerson);
		return newPerson;
	}
}
