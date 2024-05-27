package io.github.learnjakartaee.client;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import io.github.learnjakartaee.ws.AircraftInterface;
import io.github.learnjakartaee.ws.AircraftService;
import jakarta.xml.ws.BindingProvider;

public class AircraftClient {

	public static void main(String[] args) {
		// When testing with Liberty, change port to 9080
		String endpoint = "http://localhost:8080/learn-jakartaee-jaxws-server/AircraftService";
		AircraftInterface aircraftInterface = createInterface(endpoint, "admin", "password");

		String pong = aircraftInterface.ping("Hello");
		System.out.println("Ping Message: " + pong);
	}

	public static AircraftInterface createInterface(String endpoint, String username, String password) {
		URL wsdlLocation = AircraftService.class.getResource("/META-INF/services/service.wsdl");
		AircraftService aircraftService = new AircraftService(wsdlLocation,
				new QName("https://learnjaxws.github.io/learn/webservice/soap", "AircraftService"));
		AircraftInterface aircraftInterface = aircraftService.getAircraftInterfaceBinding();

		// Basic HTTP Authentication, credentials match TestCredentialValidator
		Map<String, Object> requestContext = ((BindingProvider) aircraftInterface).getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		return aircraftInterface;
	}
}
