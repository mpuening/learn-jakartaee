package io.github.learnjakartaee.aircraft.service;

public class AircraftException extends Exception {

	private static final long serialVersionUID = -1992099122814275428L;

	public AircraftException(String message) {
		super(message);
	}

	public AircraftException(String message, Throwable cause) {
		super(message, cause);
	}
}
