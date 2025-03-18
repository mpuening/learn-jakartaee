package io.github.learnjakartaee.security.identity;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

/**
 * A default implementation of an IdentityStore that is shared for applications
 * in this repository that can support test users for test cases and stand alone
 * deployments or LDAP in the case of the EAR deployment.
 */
public class DefaultAppIdentityStore implements IdentityStore {

	private CredentialValidator credentialValidator = new DelegatingCredentialValidator(
			new TestCredentialValidator(),
			new ELConfiguredLDAPCredentialValidator());

	@Override
	public CredentialValidationResult validate(Credential credential) {
		return credentialValidator.validate(credential);
	}
}
