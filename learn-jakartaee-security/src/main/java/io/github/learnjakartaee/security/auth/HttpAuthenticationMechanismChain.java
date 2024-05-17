package io.github.learnjakartaee.security.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * QUESTIONABLE
 *
 * Ideally this class should implement HttpAuthenticationMechanism. However,
 * when that is done, Open Liberty sees this class as an extra mechanism
 * despite this class being abstract. Therefore, it is up to the class that
 * extends this class to have the " implements HttpAuthenticationMechanism" code.
 */
public abstract class HttpAuthenticationMechanismChain {

	private final AuthProvider[] authProviders;

	public HttpAuthenticationMechanismChain(AuthProvider... authProviders) {
		this.authProviders = authProviders;
	}

	public AuthenticationStatus validateRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {

		for (AuthProvider authProvider : authProviders) {
			if (authProvider.appliesTo(request)) {
				return authProvider.validateRequest(request, response, httpMessageContext);
			}
		}
		// Out of providers.
		return AuthenticationStatus.SEND_FAILURE;
	}
}
