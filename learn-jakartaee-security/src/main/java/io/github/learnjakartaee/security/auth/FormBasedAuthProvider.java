package io.github.learnjakartaee.security.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FormBasedAuthProvider implements AuthProvider {

	private final String contextPath;

	private final String loginPage;

	private final String errorPage;

	private final IdentityStore identityStore;

	public FormBasedAuthProvider(String contextPath, String loginPage, String errorPage, IdentityStore identityStore) {
		this.contextPath = contextPath;
		this.loginPage = loginPage;
		this.errorPage = errorPage;
		this.identityStore = identityStore;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	@Override
	public boolean appliesTo(HttpServletRequest request) {
		boolean matchesContextPath = this.contextPath.equals(request.getContextPath());
		return matchesContextPath;
	}

	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		if (shouldValidateCredentials(httpMessageContext)) {
			return httpMessageContext.notifyContainerAboutLogin(
					this.identityStore.validate(httpMessageContext.getAuthParameters().getCredential()));
		}
		return httpMessageContext.doNothing();
	}

    protected boolean shouldValidateCredentials(HttpMessageContext httpMessageContext) {
        return httpMessageContext.getAuthParameters().getCredential() != null;
    }
}
