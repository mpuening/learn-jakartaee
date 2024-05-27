package io.github.learnjakartaee.aircraft.view;

import java.util.Collection;

import io.github.learnjakartaee.aircraft.service.AppAircraftService;
import io.github.learnjakartaee.ws.schemas.AircraftType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class AircraftBean {

	@Inject
	AppAircraftService aircraftService;

	private Collection<AircraftType> aircraft;

	private String id;

	private String designation;

	private String name;

	private String nickname;

	public Collection<AircraftType> getAircraft() {
		if (aircraft == null) {
			aircraft = aircraftService.getAircraft();
		}
		return aircraft;
	}

	public void setAircraft(Collection<AircraftType> aircraft) {
		this.aircraft = aircraft;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String addAircraft() {
		try {
			AircraftType aircraft = new AircraftType();
			aircraft.setDesignation(this.designation);
			aircraft.setName(this.name);
			aircraft.setNickname(this.nickname);
			aircraftService.addAircraft(aircraft);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/add-aircraft.xhtml";
		}
		return "/views/main/aircraft.xhtml?faces-redirect=true";
	}

	public String editAircraft(String id) {
		try {
			AircraftType aircraft = aircraftService.getAircraft(id);
			this.setId(aircraft.getId());
			this.setDesignation(aircraft.getDesignation());
			this.setName(aircraft.getName());
			this.setNickname(aircraft.getNickname());
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/aircraft.xhtml";
		}
		return "/views/main/update-aircraft.xhtml";
	}

	public String updateAircraft() {
		try {
			AircraftType updatedAircraft = new AircraftType();
			updatedAircraft.setId(this.id);
			updatedAircraft.setDesignation(this.designation);
			updatedAircraft.setName(this.name);
			updatedAircraft.setNickname(this.nickname);
			aircraftService.updateAircraft(updatedAircraft);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/edit-aircraft.xhtml";
		}
		return "/views/main/aircraft.xhtml?faces-redirect=true";
	}

	public String deleteAircraft(String id) {
		try {
			aircraftService.deleteAircraft(id);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/aircraft.xhtml";
		}
		return "/views/main/aircraft.xhtml?faces-redirect=true";
	}
}
