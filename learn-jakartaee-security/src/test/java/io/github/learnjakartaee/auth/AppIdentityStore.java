package io.github.learnjakartaee.auth;

import io.github.learnjakartaee.security.identity.CredentialValidator;
import io.github.learnjakartaee.security.identity.DelegatingCredentialValidator;
import io.github.learnjakartaee.security.identity.SpelConfiguredLDAPCredentialValidator;
import io.github.learnjakartaee.security.identity.TestCredentialValidator;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

public class AppIdentityStore implements IdentityStore {

	private CredentialValidator credentialValidator = new DelegatingCredentialValidator(
			new TestCredentialValidator(),
			new SpelConfiguredLDAPCredentialValidator());

	@Override
	public CredentialValidationResult validate(Credential credential) {
		return credentialValidator.validate(credential);
	}
}
