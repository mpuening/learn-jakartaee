package io.github.learnjakartaee.aircraft;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import io.github.learnjakartaee.aircraft.model.Aircraft;
import io.github.learnjakartaee.aircraft.service.AircraftException;
import io.github.learnjakartaee.util.api.Data;
import io.github.learnjakartaee.util.api.ProblemDetail;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RolesAllowed({ "user", "admin" })
@Path("api/aircraft")
@Named
@ApplicationScoped
public class AircraftService {

	@Inject @EJB
	protected io.github.learnjakartaee.aircraft.service.AircraftService aircraftService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponseSchema(responseCode = "200", responseDescription = "Get Aircraft", value = AircraftListResponse.class)
	public Response aircraft() {
		List<Aircraft> aircraft = aircraftService.findAll();
		Data<List<Aircraft>> data = Data.from(aircraft);
		return Response.status(Status.OK).entity(data).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponseSchema(responseCode = "200", responseDescription = "Create Aircraft", value = AircraftResponse.class)
	public Response createAircraft(Aircraft aircraft) throws AircraftException {
		try {
			aircraft = aircraftService.createAircraft(aircraft);
			Data<Aircraft> data = Data.from(aircraft);
			return Response.status(Status.OK).entity(data).build();
		} catch (AircraftException ex) {
			ProblemDetail notFound = ProblemDetail.fromStatusAndDetail(Status.BAD_REQUEST, ex.getMessage());
			return Response.status(notFound.getStatus()).entity(notFound.toJson()).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponseSchema(responseCode = "200", responseDescription = "Update Aircraft", value = AircraftResponse.class)
	public Response updateAircraft(Aircraft aircraft) throws AircraftException {
		try {
			aircraft = aircraftService.updateAircraft(aircraft);
			Data<Aircraft> data = Data.from(aircraft);
			return Response.status(Status.OK).entity(data).build();
		} catch (AircraftException ex) {
			ProblemDetail notFound = ProblemDetail.fromStatusAndDetail(Status.NOT_FOUND, ex.getMessage());
			return Response.status(notFound.getStatus()).entity(notFound.toJson()).build();
		}
	}

	@DELETE
	@Path("{id}")
	@APIResponse(responseCode = "200", description = "Delete Aircraft")
	public Response deleteAircraft(@PathParam("id") String id) throws AircraftException {
		try {
			Optional<Aircraft> aircraft = aircraftService.findById(id);
			if (aircraft.isEmpty()) {
				ProblemDetail notFound = ProblemDetail.fromStatusAndDetail(Status.NOT_FOUND, "Provided aircraft to update does not exist");
				return Response.status(notFound.getStatus()).entity(notFound.toJson()).build();
			} else {
				aircraftService.deleteAircraft(aircraft.get());
				return Response.status(Status.NO_CONTENT).build();
			}
		} catch (AircraftException ex) {
			ProblemDetail notFound = ProblemDetail.fromStatusAndDetail(Status.BAD_REQUEST, ex.getMessage());
			return Response.status(notFound.getStatus()).entity(notFound.toJson()).build();
		}
	}

	@Schema
	public static class AircraftResponse {
		Aircraft data;
	}

	@Schema
	public static class AircraftListResponse {
		List<Aircraft> data;
	}
}
