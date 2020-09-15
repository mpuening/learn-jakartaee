package io.github.learnjakartaee.controller.main.people;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * People entry form. Note, XDoclet doesn't support Java's generic type
 * notation. So we aren't using it here.
 *
 * Property names are defined in ApplicationResources.properties
 *
 * More documentation:
 * http://xdoclet.sourceforge.net/xdoclet/tags/apache-tags.html
 *
 * @struts.form name="peopleForm"
 */
public class PeopleForm extends ValidatorForm {

	private static final long serialVersionUID = 3629207086569792158L;

	/**
	 * @SuppressWarnings("rawtypes")
	 */
	private Collection people;

	private String id = "";

	private String firstName;

	private String lastName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the first name attribute of the peopleForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="2"
	 * @struts.validator-var name="maxlength" value="20"
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the last name attribute of the peopleForm object
	 *
	 * @struts.validator type="required"
	 *
	 * @struts.validator type="minlength" arg1value="${var:minlength}"
	 * @struts.validator type="maxlength" arg1value="${var:maxlength}"
	 *
	 * @struts.validator-var name="minlength" value="2"
	 * @struts.validator-var name="maxlength" value="50"
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public Collection getPeople() {
		return people;
	}

	public void setPeople(Collection people) {
		this.people = people;
	}

	public boolean isValid(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = this.validate(mapping, request);
		return errors == null || errors.isEmpty();
	}
}
