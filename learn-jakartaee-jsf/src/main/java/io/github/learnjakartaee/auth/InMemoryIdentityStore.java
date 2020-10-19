package io.github.learnjakartaee.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

	private static Map<String, String> users = Map.of(

			"admin", "password",

			"alice", "password",

			"bob", "password"

	);

	private static Map<String, Set<String>> roles = Map.of(

			"admin", Set.of("ADMIN", "USER"),

			"alice", Set.of("USER"),

			"bob", Set.of("USER")

	);

	@Override
	public CredentialValidationResult validate(Credential credential) {

		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
		
		String password = users.get(login.getCaller());
		if (password != null && password.equals(login.getPasswordAsString())) {
			return new CredentialValidationResult(login.getCaller(), roles.get(login.getCaller()));
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
	}
}
