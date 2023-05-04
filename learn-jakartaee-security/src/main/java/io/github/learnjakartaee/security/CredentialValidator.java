package io.github.learnjakartaee.security;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

/**
 * Interface similar to IdentityStore. Allows actual IdentityStores to
 * delegate to various implementations based on various conditions
 * such as a profile and environment variables.
 */
public interface CredentialValidator {

	boolean appliesTo(Credential credential);

	CredentialValidationResult validate(Credential credential);
}
