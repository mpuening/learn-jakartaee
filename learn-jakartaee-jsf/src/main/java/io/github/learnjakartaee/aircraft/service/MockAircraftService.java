package io.github.learnjakartaee.aircraft.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.github.learnjakartaee.ws.schemas.AircraftType;
import io.github.learnjakartaee.ws.schemas.ManufacturerType;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class MockAircraftService implements AppAircraftService {

	@Override
	public AircraftType getAircraft(String id) {
		return mockData.get(id);
	}

	@Override
	public Collection<AircraftType> getAircraft() {
		return mockData.values();
	}

	@Override
	public AircraftType addAircraft(AircraftType aircraft) {
		aircraft.setId(UUID.randomUUID().toString());
		mockData.put(aircraft.getId(), aircraft);
		return aircraft;
	}

	@Override
	public void updateAircraft(AircraftType updatedAircraft) {
		mockData.put(updatedAircraft.getId(), updatedAircraft);
	}

	@Override
	public void deleteAircraft(String id) {
		mockData.remove(id);
	}

	private static Map<String, AircraftType> mockData;

	static {
		mockData = new ConcurrentHashMap<>();
		AircraftType aircraft = new AircraftType();
		aircraft.setId("6b19f137-73f2-49ec-88e5-f98a97914761");
		aircraft.setDesignation("B-21");
		aircraft.setName("Raider");
		aircraft.setProduced(1);
		ManufacturerType manufacturer = new ManufacturerType();
		manufacturer.setName("Northrop Grumman");
		aircraft.setManufacturer(manufacturer);
		
		mockData.put(aircraft.getId(), aircraft);
	}
}
