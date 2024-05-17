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
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@DeclareRoles({ "admin", "user" })
@ApplicationScoped
public class SecurityConfiguration extends HttpAuthenticationMechanismChain implements HttpAuthenticationMechanism {

	/**
	 * Support Basic Auth
	 */
	public SecurityConfiguration() {
		super(basicAuthProvider(), checkAuthProvider());
	}

	/**
	 * Configures an Auth Provider to supports Basic Auth for credentials that either
	 * are in the TestCredentialValidator (when in Test Mode), or exists in an LDAP
	 * system.
	 */
	protected static BasicAuthProvider basicAuthProvider() {

		final CredentialValidator credentialValidator =

				new DelegatingCredentialValidator(
						new TestCredentialValidator(),
						new ELConfiguredLDAPCredentialValidator());

		return new BasicAuthProvider(new IdentityStore() {
			@Override
			public CredentialValidationResult validate(Credential credential) {
				return credentialValidator.validate(credential);
			}
		});
	}

	/**
	 * Provides an Auth Provider that informs users of required credentials.
	 */
	protected static CheckAuthProvider checkAuthProvider() {
		return new CheckAuthProvider();
	}
}
