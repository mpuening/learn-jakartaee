package io.github.learnjakartaee.security.auth;

import java.util.Optional;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

public class BasicAuthProvider implements AuthProvider {

	private final static String AUTH_PREFIX = "Basic ";

	private final String contextPath;

	private final IdentityStore identityStore;

	public BasicAuthProvider(String contextPath, IdentityStore identityStore) {
		this.contextPath = contextPath;
		this.identityStore = identityStore;
	}

	@Override
	public boolean appliesTo(HttpServletRequest request) {
		boolean matchesContextPath = this.contextPath.equals(request.getContextPath());
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return matchesContextPath && authorization != null && authorization.startsWith(AUTH_PREFIX);
	}

	@Override
	public AuthenticationStatus validateRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		try {
			Credential credential = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
					.filter(header -> !header.isEmpty())
					.filter(header -> header.startsWith(AUTH_PREFIX))
					.map(header -> header.substring(AUTH_PREFIX.length()))
					.map(BasicAuthenticationCredential::new)
					.orElseGet(() -> new BasicAuthenticationCredential(""));
			
			CredentialValidationResult result = identityStore.validate(credential);

			if (result.getStatus().equals(CredentialValidationResult.Status.VALID)) {
				return httpMessageContext.notifyContainerAboutLogin(result);
			} else {
				notifyContainerAboutFailedLogin(request);
			}
		} catch (IllegalArgumentException ignored) {
		} catch (IllegalStateException ignored) {
		}

		if (httpMessageContext.isProtected()) {
			response.setHeader("WWW-Authenticate", "Basic");
			notifyContainerAboutFailedLogin(request);
		}

		return httpMessageContext.doNothing();
	}
}
