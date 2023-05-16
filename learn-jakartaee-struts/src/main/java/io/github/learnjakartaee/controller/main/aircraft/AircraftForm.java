package io.github.learnjakartaee.controller.main.aircraft;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import io.github.learnjakartaee.aircraft.model.Aircraft;

/**
 * Aircraft entry form. Note, XDoclet doesn't support Java's generic type
 * notation. So we aren't using it here (See Collection).
 *
 * Property names are defined in ApplicationResources.properties
 *
 * More documentation:
 * http://xdoclet.sourceforge.net/xdoclet/tags/apache-tags.html
 *
 * @struts.form name="aircraftForm"
 */
public class AircraftForm extends ValidatorForm {

	private static final long serialVersionUID = -2703588681096358418L;

	/**
	 * @SuppressWarnings("rawtypes")
	 */
	private Collection aircraft;

	private String id;

	private String designation;

	private String name;

	private String nickname;

	private String manufacturer;

	private Integer produced;

	private String firstFlight;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the first name attribute of the aircraftForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="2"
	 * @struts.validator-var name="maxlength" value="25"
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDesignation() {
		return designation;
	}

	/**
	 * Sets the name attribute of the aircraftForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="1"
	 * @struts.validator-var name="maxlength" value="50"
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the nickname attribute of the aircraftForm object
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="1"
	 * @struts.validator-var name="maxlength" value="50"
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the manufacturer attribute of the aircraftForm object
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="1"
	 * @struts.validator-var name="maxlength" value="50"
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Sets the produced attribute of the aircraftForm object
	 *
	 * @struts.validator type="integer"
	 */
	public void setProduced(Integer produced) {
		this.produced = produced;
	}

	public Integer getProduced() {
		return produced;
	}

	/**
	 * Sets the firstFlight attribute of the aircraftForm object
	 *
	 * @struts.validator type="mask" arg1value="${var:mask}"
	 *
	 * @struts.validator-var name="mask" value="^[0-9][0-9][0-9][0-9]/[0-1][0-9]/[0-3][0-9]$"
	 */
	public void setFirstFlight(String firstFlight) {
		this.firstFlight = firstFlight;
	}

	public String getFirstFlight() {
		return firstFlight;
	}

	public LocalDate getFirstFlightAsDate() {
		LocalDate result = null;
		if (firstFlight != null && !firstFlight.isBlank()) {
			String parts[] = firstFlight.split("/");
			int year = Integer.valueOf(parts[0]);
			int month = Integer.valueOf(parts[1]);
			int day = Integer.valueOf(parts[2]);
			result = LocalDate.of(year, month, day);
		}
		return result;
	}

	/**
	 * @SuppressWarnings("rawtypes")
	 */
	public Collection getAircraft() {
		return aircraft;
	}

	/**
	 * @SuppressWarnings("rawtypes")
	 */
	public void setAircraft(Collection aircraft) {
		this.aircraft = aircraft;
	}

	public boolean isValid(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = this.validate(mapping, request);
		return errors == null || errors.isEmpty();
	}

	public Aircraft asAircraft() {
		Aircraft aircraft = new Aircraft();
		aircraft.setId(id);
		aircraft.setDesignation(designation);
		aircraft.setName(name);
		aircraft.setNickname(nickname);
		aircraft.setManufacturer(manufacturer);
		aircraft.setProduced(produced);
		aircraft.setFirstFlight(getFirstFlightAsDate());
		return aircraft;
	}

	public void applyAircraft(Aircraft aircraft) {
		setId(aircraft.getId());
		setDesignation(aircraft.getDesignation());
		setName(aircraft.getName());
		setNickname(aircraft.getNickname());
		setManufacturer(aircraft.getManufacturer());
		setProduced(aircraft.getProduced());
		setFirstFlight(aircraft.getFirstFlight() != null
				? aircraft.getFirstFlight().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
				: "");
		setFirstFlight(null);
	}
}
