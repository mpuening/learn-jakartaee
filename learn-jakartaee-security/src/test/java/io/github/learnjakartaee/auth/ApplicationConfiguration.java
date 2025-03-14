package io.github.learnjakartaee.auth;

import io.github.learnjakartaee.security.auth.AuthProvider;
import io.github.learnjakartaee.security.auth.FormBasedAuthProvider;
import io.github.learnjakartaee.security.auth.HttpAuthenticationMechanismChain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.faces.annotation.FacesConfig;
import jakarta.servlet.ServletContext;

@FacesConfig
@ApplicationScoped
//@CustomFormAuthenticationMechanismDefinition(
//		loginToContinue = @LoginToContinue(
//				loginPage = "/views/auth/login.xhtml", 
//				errorPage = "/views/auth/login.xhtml?error"))
public class ApplicationConfiguration {

    /**
	 * Support Form Based Auth
	 */
    public void initialize(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
    	HttpAuthenticationMechanismChain.registerAuthProviders(formBasedAuthProvider(context));
    }

	private AuthProvider formBasedAuthProvider(ServletContext context) {
		return new FormBasedAuthProvider(context.getContextPath(),
				"/views/auth/login.xhtml", "/views/auth/login.xhtml?error", new AppIdentityStore());
	}
}
