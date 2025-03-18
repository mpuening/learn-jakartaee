package io.github.learnjakartaee.config;

import io.github.learnjakartaee.security.auth.CheckAuthProvider;
import io.github.learnjakartaee.security.auth.HttpAuthenticationMechanismChain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.servlet.ServletContext;

/**
 * This class isn't needed to do anything. However, this class seems to do
 * something on Glassfish to keep it on Jakarta 10 APIs rather than Jakarta 11, which
 * we don't have at the moment (Looking at you HttpAuthenticationMechanismHandler).
 */
@ApplicationScoped
public class IndexSecurityConfiguration {

    public void initialize(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
    	HttpAuthenticationMechanismChain.registerAuthProviders(checkAuthProvider(context));
    }

	protected static CheckAuthProvider checkAuthProvider(ServletContext context) {
		return new CheckAuthProvider(context.getContextPath());
	}

}
