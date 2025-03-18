package io.github.learnjakartaee.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A custom implementation of an HttpAuthenticationMechanism that tries to mimic
 * the Spring Security Chain so that an application can use multiple mechanisms (or
 * kinds) of authentications. At the time of writing this, Jakarta EE restricts developers
 * to a single mechanism. Therefore having an EAR file with a web app having a login page
 * and another API using basic authentication is not possible.
 *
 * To use this class, just register implementations of AuthProvider's to this class.
 * (And do not use built-in mechanisms such as @CustomFormAuthenticationMechanismDefinition)
 */
@ApplicationScoped
@AutoApplySession
@SupportCustomLoginToContinue
//@Typed(HttpAuthenticationMechanism.class)
public class HttpAuthenticationMechanismChain implements HttpAuthenticationMechanism {

	protected final Logger LOG = LoggerFactory.getLogger(HttpAuthenticationMechanismChain.class);

	private static final List<AuthProvider> AUTH_PROVIDERS = new ArrayList<>();

	public HttpAuthenticationMechanismChain() {
		LOG.info("LOADING HttpAuthenticationMechanismChain...");
	}

	public AuthenticationStatus validateRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		
		for (AuthProvider authProvider : AUTH_PROVIDERS) {
			if (authProvider.appliesTo(request)) {
				return authProvider.validateRequest(request, response, httpMessageContext);
			}
		}
		// Out of providers.
		return AuthenticationStatus.SEND_FAILURE;
	}

	// ==========================================

	private static final HashMap<String, FormBasedAuthProvider> FORM_BASED_AUTH_PROVIDERS = new HashMap<>();

	public static void registerAuthProviders(AuthProvider... authProviders) {
		for (AuthProvider authProvider : authProviders) {
			AUTH_PROVIDERS.add(authProvider);
			if (authProvider instanceof FormBasedAuthProvider) {
				FormBasedAuthProvider formBasedAuthProvider = (FormBasedAuthProvider)authProvider;
				FORM_BASED_AUTH_PROVIDERS.put(formBasedAuthProvider.getContextPath(), formBasedAuthProvider);
			}
		}
	}

	public static String getLoginPage(String contextPath) {
		FormBasedAuthProvider provider = FORM_BASED_AUTH_PROVIDERS.get(contextPath);
		return provider != null ? provider.getLoginPage() : null;
	}
	
	public static String getErrorPage(String contextPath) {
		FormBasedAuthProvider provider = FORM_BASED_AUTH_PROVIDERS.get(contextPath);
		return provider != null ? provider.getErrorPage() : null;
	}
}
