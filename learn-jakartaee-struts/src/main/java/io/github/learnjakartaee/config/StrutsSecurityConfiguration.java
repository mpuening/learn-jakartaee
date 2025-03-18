package io.github.learnjakartaee.config;

import io.github.learnjakartaee.security.auth.AuthProvider;
import io.github.learnjakartaee.security.auth.FormBasedAuthProvider;
import io.github.learnjakartaee.security.auth.HttpAuthenticationMechanismChain;
import io.github.learnjakartaee.security.identity.DefaultAppIdentityStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.servlet.ServletContext;

/**
 * To support multiple of types of authentication, we transfer control to a
 * single HttpAuthenticationMechanism, and not use
 * a @CustomFormAuthenticationMechanismDefinition annotation.
 */
//@CustomFormAuthenticationMechanismDefinition(
//		loginToContinue = @LoginToContinue(
//				loginPage = "/login.do", 
//				errorPage = "/login.do?error"))
@ApplicationScoped
public class StrutsSecurityConfiguration {

    public void initialize(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
    	HttpAuthenticationMechanismChain.registerAuthProviders(formBasedAuthProvider(context));
    }

	private AuthProvider formBasedAuthProvider(ServletContext context) {
		return new FormBasedAuthProvider(context.getContextPath(),
				"/login.do", "/login.do?error", new DefaultAppIdentityStore());
	}

}
