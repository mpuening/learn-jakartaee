package io.github.learnjakartaee.config;

import io.github.learnjakartaee.auth.AppIdentityStore;
import io.github.learnjakartaee.security.auth.AuthProvider;
import io.github.learnjakartaee.security.auth.FormBasedAuthProvider;
import io.github.learnjakartaee.security.auth.HttpAuthenticationMechanismChain;
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
//				loginPage = "/views/auth/login.xhtml", 
//				errorPage = "/views/auth/login.xhtml?error"))
@ApplicationScoped
public class JsfSecurityConfiguration {

    public void initialize(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
    	HttpAuthenticationMechanismChain.registerAuthProviders(formBasedAuthProvider(context));
    }

	private AuthProvider formBasedAuthProvider(ServletContext context) {
		return new FormBasedAuthProvider(context.getContextPath(),
				"/views/auth/login.xhtml", "/views/auth/login.xhtml?error", new AppIdentityStore());
	}

}
