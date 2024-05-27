package io.github.learnjakartaee.aircraft.service;

import java.util.Collection;

import io.github.learnjakartaee.client.AircraftClient;
import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.messages.AcknowledgeAircraftType;
import io.github.learnjakartaee.ws.messages.CreateAircraftType;
import io.github.learnjakartaee.ws.messages.DeleteAircraftType;
import io.github.learnjakartaee.ws.messages.GetAircraftType;
import io.github.learnjakartaee.ws.messages.ShowAircraftType;
import io.github.learnjakartaee.ws.messages.UpdateAircraftType;
import io.github.learnjakartaee.ws.schemas.AircraftType;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class WsAircraftService implements AppAircraftService {

	private final AircraftInterface aircraftInterface;

	public WsAircraftService() throws Exception {
		ExpressionEvaluator evaluator = new ELExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
		String endpoint = environment.getProperty("AircraftService.endpoint", "jsf.aircraft.service.endpoint", "http://localhost:8080/learn-jakartaee-jaxws-server/AircraftService");
		String username = environment.getProperty("AircraftService.username", "jsf.aircraft.service.username", "username");
		String password = environment.getProperty("AircraftService.password", "jsf.aircraft.service.password", "password");

		aircraftInterface = AircraftClient.createInterface(endpoint, username, password);
	}
	
	@Override
	public AircraftType getAircraft(String id) {
		GetAircraftType get = new GetAircraftType();
		get.setId(id);
		ShowAircraftType show = aircraftInterface.getAircraft(get);
		if (show.getAircrafts() != null && !show.getAircrafts().isEmpty()) {
			return show.getAircrafts().get(0);
		}
		return null;
	}

	@Override
	public Collection<AircraftType> getAircraft() {
		GetAircraftType get = new GetAircraftType();
		ShowAircraftType show = aircraftInterface.getAircraft(get);
		return show.getAircrafts();
	}

	@Override
	public AircraftType addAircraft(AircraftType aircraft) {
		CreateAircraftType create = new CreateAircraftType();
		create.setAircraft(aircraft);
		AcknowledgeAircraftType ack = aircraftInterface.createAircraft(create);
		aircraft.setId(ack.getId());
		return aircraft;
	}

	@Override
	public void updateAircraft(AircraftType aircraft) {
		UpdateAircraftType update = new UpdateAircraftType();
		update.setAircraft(aircraft);
		aircraftInterface.updateAircraft(update);
	}

	@Override
	public void deleteAircraft(String id) {
		DeleteAircraftType delete = new DeleteAircraftType();
		AircraftType aircraft = new AircraftType();
		aircraft.setId(id);
		delete.setAircraft(aircraft );
		aircraftInterface.deleteAircraft(delete);
	}
}
