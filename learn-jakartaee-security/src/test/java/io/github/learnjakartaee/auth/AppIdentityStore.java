package io.github.learnjakartaee.auth;

import io.github.learnjakartaee.security.CredentialValidator;
import io.github.learnjakartaee.security.SpelConfiguredLDAPCredentialValidator;
import io.github.learnjakartaee.security.TestCredentialValidator;
import io.github.learnjakartaee.security.DelegatingCredentialValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

	private CredentialValidator credentialValidator = new DelegatingCredentialValidator(
			new TestCredentialValidator(),
			new SpelConfiguredLDAPCredentialValidator());

	@Override
	public CredentialValidationResult validate(Credential credential) {
		return credentialValidator.validate(credential);
	}
}
