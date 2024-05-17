package io.github.learnjakartaee.aircraft.model;

import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Transient;

/**
 * This class provides a base class similar to Spring's AbstractPersistable
 * class. However, what is different is that this implementation does not
 * specify what the "Id" is, but at least still provides an implementation of
 * "toString()", "equals()", "hashCode()" methods.
 * 
 * Classes that extend this class should specify an "id" property, or provide a
 * transient getId() method that delegates to what the "id" is of the entity.
 */
public abstract class AbstractEntity<PK extends Serializable> {

	/**
	 * Returns the id of the entity.
	 *
	 * @return The id. Can be null.
	 */
	public abstract PK getId();

	@Transient
	@JsonbTransient
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public String toString() {
		return String.format("Entity of type: %s with id: %s", this.getClass().getName(), getId());
	}

	@Override
	public boolean equals(Object other) {

		if (other == null) {
			return false;
		}
		if (this == other) {
			return true;
		}
		if (getClass() != other.getClass()) {
			return false;
		}

		AbstractEntity<?> otherEntity = (AbstractEntity<?>) other;
		return (this.getId() == null) ? false : this.getId().equals(otherEntity.getId());
	}

	
	@Override
	public int hashCode() {

		int hashCode = 17;
		hashCode += (getId() == null) ? 0 : (getId().hashCode() * 31);
		return hashCode;
	}
}
