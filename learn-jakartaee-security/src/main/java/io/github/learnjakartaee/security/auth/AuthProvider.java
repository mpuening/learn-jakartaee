package io.github.learnjakartaee.security.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthProvider {

	/**
	 * Request attribute to indicate the result of the authentication.
	 */
	public static final String AUTH_VALIDATION_RESULT = "AUTH_VALIDATION_RESULT";

	/**
	 * See ErrorResponseFilter for how this is handled
	 */
	default void notifyContainerAboutFailedLogin(HttpServletRequest request) {
		request.setAttribute(AUTH_VALIDATION_RESULT, "401");
	}

	/**
	 * Request attribute to indicate the user should authenticate.
	 */
	public static final String REQUEST_USER_AUTHORIZATION = "REQUEST_USER_AUTHORIZATION";

	/**
	 * See ErrorResponseFilter for how this is handled
	 */
	default void notifyUserToAuthenticate(HttpServletRequest request) {
		request.setAttribute(REQUEST_USER_AUTHORIZATION, true);
	}

	boolean appliesTo(HttpServletRequest request);

	AuthenticationStatus validateRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException;


}
