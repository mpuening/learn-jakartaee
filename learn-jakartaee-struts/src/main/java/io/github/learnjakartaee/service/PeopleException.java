package io.github.learnjakartaee.service;

public class PeopleException extends Exception {
	private static final long serialVersionUID = 7158791101682940173L;

	public PeopleException(String message, Throwable cause) {
		super(message, cause);
	}

	public PeopleException(String message) {
		super(message);
	}

}
