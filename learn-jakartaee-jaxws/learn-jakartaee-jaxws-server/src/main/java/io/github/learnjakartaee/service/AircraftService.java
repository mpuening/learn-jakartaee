package io.github.learnjakartaee.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import io.github.learnjakartaee.aircraft.service.AircraftException;
import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.messages.AcknowledgeAircraftType;
import io.github.learnjakartaee.ws.messages.CreateAircraftType;
import io.github.learnjakartaee.ws.messages.DeleteAircraftType;
import io.github.learnjakartaee.ws.messages.GetAircraftType;
import io.github.learnjakartaee.ws.messages.ShowAircraftType;
import io.github.learnjakartaee.ws.messages.UpdateAircraftType;
import io.github.learnjakartaee.ws.schemas.AircraftType;
import io.github.learnjakartaee.ws.schemas.ManufacturerType;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

@RolesAllowed({ "user", "admin" })
@WebService(serviceName = "AircraftService", endpointInterface = "io.github.learnjakartaee.ws.AircraftInterface")
public class AircraftService implements AircraftInterface {

	@Inject @EJB
	protected io.github.learnjakartaee.aircraft.service.AircraftService aircraftService;

	@Override
	public ShowAircraftType getAircraft(GetAircraftType getAircraftType) {
		//Optional<String> designation = Optional.of(getAircraftType.getDesignation());
		//Optional<String> name = Optional.of(getAircraftType.getName());
		List<Aircraft> aircrafts = new ArrayList<>();
		if (getAircraftType.getId() != null) {
			Optional<Aircraft> aircraft = aircraftService.findById(getAircraftType.getId());
			if (aircraft.isPresent()) {
				aircrafts.add(aircraft.get());
			}
		}
		else if (getAircraftType.getDesignation() != null) {
			Optional<Aircraft> aircraft = aircraftService.findByDesignation(getAircraftType.getDesignation());
			if (aircraft.isPresent()) {
				aircrafts.add(aircraft.get());
			}
		}
		else  {
			// TODO find by name
			aircrafts = aircraftService.findAll();
		}
		List<AircraftType> searchResults = aircrafts.stream()
				.map(a -> {
					AircraftType aircraft = new AircraftType();
					aircraft.setId(a.getId());
					aircraft.setDesignation(a.getDesignation());
					aircraft.setName(a.getName());
					aircraft.setNickname(a.getNickname());
					aircraft.setProduced(a.getProduced());
					if (a.getFirstFlight() != null) {
						Date date = java.util.Date
								.from(a.getFirstFlight().atStartOfDay(ZoneId.systemDefault())
								.toInstant());
						aircraft.setFirstFlight(date);
					}
					if (a.getManufacturer() != null) {
						ManufacturerType manufacturer = new ManufacturerType();
						manufacturer.setName(a.getManufacturer());
						aircraft.setManufacturer(manufacturer);
					}
					return aircraft;
				})
				.collect(Collectors.toList());
		ShowAircraftType response = new ShowAircraftType();
		response.getAircrafts().addAll(searchResults);
		return response;
	}

	@Override
	public AcknowledgeAircraftType createAircraft(CreateAircraftType createAircraftType) {
		AcknowledgeAircraftType response = new AcknowledgeAircraftType();
		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setId(null);
			aircraft.setDesignation(createAircraftType.getAircraft().getDesignation());
			aircraft.setName(createAircraftType.getAircraft().getName());
			aircraft.setNickname(createAircraftType.getAircraft().getNickname());
			if (createAircraftType.getAircraft().getFirstFlight() != null) {
				LocalDate date = createAircraftType.getAircraft().getFirstFlight()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				aircraft.setFirstFlight(date);
			}
			if (createAircraftType.getAircraft().getManufacturer() != null) {
				aircraft.setManufacturer(createAircraftType.getAircraft().getManufacturer().getName());
			}
			aircraft.setProduced(createAircraftType.getAircraft().getProduced());

			aircraft = aircraftService.createAircraft(aircraft);
			response.setId(aircraft.getId());
			response.setCode(200);
			response.setStatus("OK");
		} catch (AircraftException ex) {
			response.setCode(400);
			response.setStatus("Bad Request");
			response.setMessage(ex.getMessage());
		}
		return response;
	}

	@Override
	public AcknowledgeAircraftType updateAircraft(UpdateAircraftType updateAircraftType) {
		AcknowledgeAircraftType response = new AcknowledgeAircraftType();
		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setId(updateAircraftType.getAircraft().getId());
			aircraft.setDesignation(updateAircraftType.getAircraft().getDesignation());
			aircraft.setName(updateAircraftType.getAircraft().getName());
			aircraft.setNickname(updateAircraftType.getAircraft().getNickname());
			if (updateAircraftType.getAircraft().getFirstFlight() != null) {
				LocalDate date = updateAircraftType.getAircraft().getFirstFlight()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				aircraft.setFirstFlight(date);
			}
			if (updateAircraftType.getAircraft().getManufacturer() != null) {
				aircraft.setManufacturer(updateAircraftType.getAircraft().getManufacturer().getName());
			}
			aircraft.setProduced(updateAircraftType.getAircraft().getProduced());
			
			aircraft = aircraftService.updateAircraft(aircraft);
			response.setId(aircraft.getId());
			response.setCode(200);
			response.setStatus("OK");
		} catch (AircraftException ex) {
			response.setCode(400);
			response.setStatus("Bad Request");
			response.setMessage(ex.getMessage());
		}
		return response;
	}

	@Override
	public AcknowledgeAircraftType deleteAircraft(DeleteAircraftType deleteAircraftType) {
		AcknowledgeAircraftType response = new AcknowledgeAircraftType();
		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setId(deleteAircraftType.getAircraft().getId());
			aircraft.setDesignation(deleteAircraftType.getAircraft().getDesignation());
			aircraft.setName(deleteAircraftType.getAircraft().getName());
			aircraft.setNickname(deleteAircraftType.getAircraft().getNickname());
			if (deleteAircraftType.getAircraft().getFirstFlight() != null) {
				LocalDate date = deleteAircraftType.getAircraft().getFirstFlight()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				aircraft.setFirstFlight(date);
			}
			if (deleteAircraftType.getAircraft().getManufacturer() != null) {
				aircraft.setManufacturer(deleteAircraftType.getAircraft().getManufacturer().getName());
			}
			aircraft.setProduced(deleteAircraftType.getAircraft().getProduced());
			
			aircraftService.deleteAircraft(aircraft);
			response.setId(aircraft.getId());
			response.setCode(200);
			response.setStatus("OK");
		} catch (AircraftException ex) {
			response.setCode(400);
			response.setStatus("Bad Request");
			response.setMessage(ex.getMessage());
		}
		return response;
	}

	@Override
	public String ping(String message) {
		return message;
	}

}
