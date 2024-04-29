package io.github.learnjakartaee.security.identity;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

/**
 * Implementation of CredentialValidator that delegates to other validators.
 * Each validator gets a chance to determine if it applies. If not, the next
 * validator in the list gets a chance. First validator to decides it applies
 * gets to decide result.
 */
public class DelegatingCredentialValidator implements CredentialValidator {

	private final CredentialValidator[] credentialValidators;

	public DelegatingCredentialValidator(CredentialValidator... credentialValidators) {
		this.credentialValidators = credentialValidators;
	}

	@Override
	public boolean appliesTo(Credential credential) {
		return true;
	}

	@Override
	public CredentialValidationResult validate(Credential credential) {
		for (CredentialValidator credentialValidator : credentialValidators) {
			if (credentialValidator.appliesTo(credential)) {
				return credentialValidator.validate(credential);
			}
		}
		// Out of validators, fail.
		return CredentialValidationResult.NOT_VALIDATED_RESULT;
	}

}
