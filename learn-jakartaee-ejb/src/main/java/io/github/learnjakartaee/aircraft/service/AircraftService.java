package io.github.learnjakartaee.aircraft.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Stateless
public class AircraftService implements Serializable {

	private static final long serialVersionUID = -566074938048500814L;

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * Find All. One should really consider paging... ;-)
	 */
	public List<Aircraft> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aircraft> query = criteriaBuilder.createQuery(Aircraft.class);
		Root<Aircraft> fromAircraft = query.from(Aircraft.class);
		CriteriaQuery<Aircraft> selectAircraft = query
				.select(fromAircraft)
				.orderBy(criteriaBuilder.asc(fromAircraft.get("designation")));
		TypedQuery<Aircraft> findAll = entityManager.createQuery(selectAircraft);
		return findAll.getResultList();
	}

	public Optional<Aircraft> findById(String id) {
		return findByProperty("id", id);
	}

	public Optional<Aircraft> findByDesignation(String designation) {
		return findByProperty("designation", designation);
	}

	protected Optional<Aircraft> findByProperty(String property, String value) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aircraft> query = criteriaBuilder.createQuery(Aircraft.class);
		Root<Aircraft> fromAircraft = query.from(Aircraft.class);
		CriteriaQuery<Aircraft> selectAircraft = query
				.select(fromAircraft)
				.where(criteriaBuilder.equal(fromAircraft.get(property), value));
		TypedQuery<Aircraft> findByDesignation = entityManager.createQuery(selectAircraft);
		List<Aircraft> results = findByDesignation.getResultList();
		return Optional.ofNullable(results.size() > 0 ? results.get(0) : null);
	}

	public Aircraft createNewAircraft(Aircraft aircraft) throws AppException {
		if (aircraft == null || !aircraft.isNew()) {
			throw new AppException("Please provide a new unsaved aircraft to save");
		}
		if (aircraft.getDesignation() == null || aircraft.getName() == null) {
			throw new AppException("Please provide a name and designation for the aircraft");
		}
		// Buy a lottery ticket if an id collision occurs
		String id = UUID.randomUUID().toString();
		aircraft.setId(id);
		entityManager.persist(aircraft);
		return aircraft;
	}
	
	public Aircraft updateExistingAircraft(Aircraft update) throws AppException {
		if (update == null || update.isNew()) {
			throw new AppException("Please provide an existing aircraft to update");
		}
		Optional<Aircraft> existing = findById(update.getId());
		if (existing.isEmpty()) {
			throw new AppException("Provided aircraft to update does not exist");
		}
		if (update.getNickname() != null) {
			existing.get().setNickname(update.getNickname());
		}
		if (update.getManufacturer() != null) {
			existing.get().setManufacturer(update.getManufacturer());
		}
		if (update.getProduced() != null) {
			existing.get().setProduced(update.getProduced());
		}
		if (update.getFirstFlight() != null) {
			existing.get().setFirstFlight(update.getFirstFlight());
		}
		entityManager.persist(existing.get());
		return existing.get();
	}

	public void deleteExistingAircraft(Aircraft delete) throws AppException {
		if (delete == null || delete.isNew()) {
			throw new AppException("Please provide an existing aircraft to delete");
		}
		// CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		// CriteriaDelete<Aircraft> query = criteriaBuilder.createCriteriaDelete(Aircraft.class);
		// Root<Aircraft> fromAircraft = query.from(Aircraft.class);
		// query.where(criteriaBuilder.equal(fromAircraft.get("id"), delete.getId()));
		// int numberOfRowsUpdated = entityManager.createQuery(query).executeUpdate();
	    int numberOfRowsUpdated = entityManager
	    		.createQuery("delete from Aircraft a where a.id = :id")
	    		.setParameter("id", delete.getId())
	    		.executeUpdate();
		if (numberOfRowsUpdated != 1) {
			throw new AppException("Expected to delete one record, got " + numberOfRowsUpdated);
		}
	}
}
