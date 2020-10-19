package io.github.learnjakartaee.people.controller;

import java.util.Collection;
import java.util.UUID;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import io.github.learnjakartaee.model.Person;
import io.github.learnjakartaee.service.PeopleException;
import io.github.learnjakartaee.service.PeopleService;

@Named
@ManagedBean
@RequestScoped
public class PeopleBean {

	@Inject
	private PeopleService peopleService;

	private Collection<Person> people;

	private String id;

	private String firstName;

	private String lastName;

	public Collection<Person> getPeople() {
		if (people == null) {
			people = peopleService.getPeople();
		}
		return people;
	}

	public void setPeople(Collection<Person> people) {
		this.people = people;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String addPerson(PeopleBean newPersonBean) {
		try {
			peopleService.addPerson(newPersonBean.getFirstName(), newPersonBean.getLastName());
		} catch (PeopleException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/add-person.xhtml";
		}
		return "/views/main/people.xhtml?faces-redirect=true";
	}

	public String editPerson(String id) {
		try {
			Person person = peopleService.getPerson(id);
			this.setId(person.getId().toString());
			this.setFirstName(person.getFirstName());
			this.setLastName(person.getLastName());
		} catch (PeopleException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/people.xhtml";
		}
		return "/views/main/update-person.xhtml";
	}

	public String updatePerson(PeopleBean updatedPersonBean) {
		try {
			Person updatedPerson = new Person();
			updatedPerson.setId(UUID.fromString(updatedPersonBean.getId()));
			updatedPerson.setFirstName(updatedPersonBean.getFirstName());
			updatedPerson.setLastName(updatedPersonBean.getLastName());
			peopleService.updatePerson(updatedPerson);
		} catch (PeopleException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/edit-person.xhtml";
		}
		return "/views/main/people.xhtml?faces-redirect=true";
	}

	public String deletePerson(String id) {
		try {
			peopleService.deletePerson(id);
		} catch (PeopleException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "/views/main/people.xhtml";
		}
		return "/views/main/people.xhtml?faces-redirect=true";
	}
}
