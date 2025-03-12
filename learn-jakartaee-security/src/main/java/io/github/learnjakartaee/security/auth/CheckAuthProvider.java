package io.github.learnjakartaee.security.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * If end-point is protected, ensure caller receives an authenticate
 * response header.
 *
 * QUESTIONABLE Should we support this? 
 */
public class CheckAuthProvider implements AuthProvider {

	private final String contextPath;

	public CheckAuthProvider(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public boolean appliesTo(HttpServletRequest request) {
		boolean matchesContextPath = this.contextPath.equals(request.getContextPath());
		return matchesContextPath;
	}

	@Override
	public AuthenticationStatus validateRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {

		// App servers are inconsistent whether the context isProtected
		if (httpMessageContext.isProtected()) {
			response.setHeader("WWW-Authenticate", "Basic");
			notifyContainerAboutFailedLogin(request);
			notifyUserToAuthenticate(request);
			// GlassFish's version will always return HTML response
			//return httpMessageContext.responseUnauthorized();
		}

		return httpMessageContext.doNothing();
	}
}
