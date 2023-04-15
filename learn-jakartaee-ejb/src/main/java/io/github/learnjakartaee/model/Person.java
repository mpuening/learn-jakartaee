package io.github.learnjakartaee.model;

import java.io.Serializable;
import java.util.UUID;

@Deprecated
public class Person implements Serializable {

	private static final long serialVersionUID = 3345968133996920537L;

	private UUID id;

	private String firstName;

	private String lastName;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUniqueKey() {
		return this.getLastName() + "," + this.getFirstName();
	}
}
