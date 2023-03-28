package io.github.learnjakartaee.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.messages.AcknowledgeAircraftType;
import io.github.learnjakartaee.ws.messages.GetAircraftType;
import io.github.learnjakartaee.ws.messages.ShowAircraftType;
import io.github.learnjakartaee.ws.messages.UpdateAircraftType;
import io.github.learnjakartaee.ws.schemas.AircraftType;
import io.github.learnjakartaee.ws.schemas.ManufacturerType;
import jakarta.jws.WebService;

@WebService(endpointInterface = "io.github.learnjakartaee.ws.AircraftInterface")
public class AircraftService implements AircraftInterface {

	@Override
	public String ping(String message) {
		return message;
	}

	@Override
	public ShowAircraftType getAircraft(GetAircraftType getAircraftType) {
		Optional<String> designation = Optional.of(getAircraftType.getDesignation());
		Optional<String> name = Optional.of(getAircraftType.getName());
		List<AircraftType> searchResults = aircrafts.values().stream()
				.filter(aircraft -> (aircraft.getDesignation().startsWith(designation.orElse(""))
						&& aircraft.getName().startsWith(name.orElse(""))))
				.collect(Collectors.toList());
		ShowAircraftType response = new ShowAircraftType();
		response.getAircrafts().addAll(searchResults);
		return response;
	}

	@Override
	public AcknowledgeAircraftType updateAircraft(UpdateAircraftType updateAircraftType) {
		aircrafts.put(updateAircraftType.getAircraft().getDesignation(),
				updateAircraftType.getAircraft());
		AcknowledgeAircraftType response = new AcknowledgeAircraftType();
		response.setCode(200);
		response.setStatus("SUCCESS");
		return response;
	}

	protected final Map<String, AircraftType> aircrafts = new ConcurrentHashMap<>();

	public AircraftService() {
		ManufacturerType generalDynamics = new ManufacturerType();
		generalDynamics.setName("General Dynamics");
		generalDynamics.setHeadquarters("West Falls Church, Virginia");
		Date founded = Date
				.from(LocalDate.of(1899, Month.FEBRUARY, 7).atStartOfDay(ZoneId.systemDefault()).toInstant());
		generalDynamics.setFounded(founded);

		AircraftType f16 = new AircraftType();
		f16.setDesignation("F-16");
		f16.setName("Fighting Falcon");
		f16.setNickname("Viper");
		f16.setManufacturer(generalDynamics);
		Date firstFlight = Date
				.from(LocalDate.of(1974, Month.JANUARY, 20).atStartOfDay(ZoneId.systemDefault()).toInstant());
		f16.setFirstFlight(firstFlight);
		f16.setProduced(4_573);

		aircrafts.put(f16.getDesignation(), f16);

		ManufacturerType boeing = new ManufacturerType();
		boeing.setName("Boeing");
		boeing.setHeadquarters("Chicago, Illinois");
		founded = Date.from(LocalDate.of(1916, Month.JULY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		boeing.setFounded(founded);

		AircraftType b17 = new AircraftType();
		b17.setDesignation("B-17");
		b17.setName("Flying Fortress");
		b17.setNickname("None");
		b17.setManufacturer(boeing);
		firstFlight = Date.from(LocalDate.of(1935, Month.JULY, 28).atStartOfDay(ZoneId.systemDefault()).toInstant());
		b17.setFirstFlight(firstFlight);
		b17.setProduced(12_731);

		aircrafts.put(b17.getDesignation(), b17);

		ManufacturerType lockheed = new ManufacturerType();
		lockheed.setName("Lockheed");
		lockheed.setHeadquarters("Burbank, California");
		founded = Date.from(LocalDate.of(1912, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		lockheed.setFounded(founded);

		AircraftType sr71 = new AircraftType();
		sr71.setDesignation("SR-71");
		sr71.setName("Blackbird");
		sr71.setNickname("None");
		sr71.setManufacturer(lockheed);
		firstFlight = Date
				.from(LocalDate.of(1964, Month.DECEMBER, 22).atStartOfDay(ZoneId.systemDefault()).toInstant());
		sr71.setFirstFlight(firstFlight);
		sr71.setProduced(32);

		aircrafts.put(sr71.getDesignation(), sr71);
	}
}
