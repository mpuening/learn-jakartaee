package io.github.learnjakartaee.aircraft.service;

public class AppException extends Exception {

	private static final long serialVersionUID = -1992099122814275428L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
}
