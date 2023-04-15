package io.github.learnjakartaee.aircraft.service;

import java.io.Serializable;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AircraftService implements Serializable {

	private static final long serialVersionUID = -566074938048500814L;

	@PersistenceContext
	EntityManager entityManager;
	
	public String sayHello() {
		return "Hello " + entityManager;
	}
}
