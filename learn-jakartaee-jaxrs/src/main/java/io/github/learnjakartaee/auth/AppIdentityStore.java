package io.github.learnjakartaee.auth;

import io.github.learnjakartaee.security.CredentialValidator;
import io.github.learnjakartaee.security.TestCredentialValidator;
import io.github.learnjakartaee.security.DelegatingCredentialValidator;
import io.github.learnjakartaee.security.ELConfiguredLDAPCredentialValidator;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@DeclareRoles({ "admin", "user" })
@BasicAuthenticationMechanismDefinition
@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

	private CredentialValidator credentialValidator = new DelegatingCredentialValidator(
			new TestCredentialValidator(),
			new ELConfiguredLDAPCredentialValidator());

	@Override
	public CredentialValidationResult validate(Credential credential) {
		return credentialValidator.validate(credential);
	}
}
