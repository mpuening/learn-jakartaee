package io.github.learnjakartaee.aircraft.service;

import java.util.Collection;

import io.github.learnjakartaee.ws.schemas.AircraftType;

/**
 * This class is called "AppAircraftService" so as not the conflict with the EJB
 * which would have the same name and live in the same package.
 */
public interface AppAircraftService {

	AircraftType getAircraft(String id);

	Collection<AircraftType> getAircraft();

	AircraftType addAircraft(AircraftType aircraft);

	void updateAircraft(AircraftType updatedAircraft);

	void deleteAircraft(String id);
}
