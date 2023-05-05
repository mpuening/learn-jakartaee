package io.github.learnjakartaee.aircraft.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AIRCRAFT")
public class Aircraft extends AbstractEntity<String> {

	@Id
	@Column(name = "ID", columnDefinition = "CHAR(36)", nullable = false, unique = true, length = 36)
	private String id;

	@Column(name = "DESIGNATION", columnDefinition = "VARCHAR(25)", nullable = false, length = 25)
	private String designation;

	@Column(name = "NAME", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
	private String name;

	@Column(name = "NICKNAME", columnDefinition = "VARCHAR(50)", length = 50)
	private String nickname;

	@Column(name = "MANUFACTURER", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
	private String manufacturer;

	@Column(name = "PRODUCED")
	private Integer produced;

	@Column(name = "FIRST_FLIGHT")
	private LocalDate firstFlight;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getProduced() {
		return produced;
	}

	public void setProduced(Integer produced) {
		this.produced = produced;
	}

	public LocalDate getFirstFlight() {
		return firstFlight;
	}

	public void setFirstFlight(LocalDate firstFlight) {
		this.firstFlight = firstFlight;
	}
}
