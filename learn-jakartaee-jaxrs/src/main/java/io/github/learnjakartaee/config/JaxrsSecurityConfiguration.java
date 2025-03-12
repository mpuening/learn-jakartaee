package io.github.learnjakartaee.config;

import io.github.learnjakartaee.security.auth.BasicAuthProvider;
import io.github.learnjakartaee.security.auth.CheckAuthProvider;
import io.github.learnjakartaee.security.auth.HttpAuthenticationMechanismChain;
import io.github.learnjakartaee.security.identity.CredentialValidator;
import io.github.learnjakartaee.security.identity.DelegatingCredentialValidator;
import io.github.learnjakartaee.security.identity.ELConfiguredLDAPCredentialValidator;
import io.github.learnjakartaee.security.identity.TestCredentialValidator;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.ServletContext;

@DeclareRoles({ "admin", "user" })
@ApplicationScoped
public class JaxrsSecurityConfiguration {

    /**
	 * Support Basic Auth
	 */
    public void initialize(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
    	HttpAuthenticationMechanismChain.registerAuthProviders(basicAuthProvider(context), checkAuthProvider(context));
    }

	/**
	 * Configures an Auth Provider to supports Basic Auth for credentials that either
	 * are in the TestCredentialValidator (when in Test Mode), or exists in an LDAP
	 * system.
	 */
	protected static BasicAuthProvider basicAuthProvider(ServletContext context) {

		final CredentialValidator credentialValidator =

				new DelegatingCredentialValidator(
						new TestCredentialValidator(),
						new ELConfiguredLDAPCredentialValidator());

		return new BasicAuthProvider(context.getContextPath(), new IdentityStore() {
			@Override
			public CredentialValidationResult validate(Credential credential) {
				return credentialValidator.validate(credential);
			}
		});
	}

	/**
	 * Provides an Auth Provider that informs users of required credentials.
	 */
	protected static CheckAuthProvider checkAuthProvider(ServletContext context) {
		return new CheckAuthProvider(context.getContextPath());
	}
}
