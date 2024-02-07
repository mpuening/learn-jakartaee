package io.github.learnjakartaee.security;

import java.util.Map;
import java.util.Set;

import io.github.learnjakartaee.env.ConfigurableEnvironment;
import io.github.learnjakartaee.env.ExpressionEvaluator;
import io.github.learnjakartaee.env.el.ELExpressionEvaluator;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

/**
 * Simple credential validator used for testing only. A set of hard-coded users
 * and roles are provided as default, but can be overridden.
 *
 * This validator is enabled only when TEST_USERS_ALLOWED env variable or
 * test.users.allowed system property is set to true.
 */
public class TestCredentialValidator implements CredentialValidator {

	public static final String TEST_USERS_ENABLED_PROPERTY = "test.users.enabled";

	private static Map<String, String> USERS = Map.of(

			"admin", "password",

			"alice", "password",

			"bob", "password"

	);

	private static Map<String, Set<String>> ROLES = Map.of(

			"admin", Set.of("admin", "user"),

			"alice", Set.of("user"),

			"bob", Set.of("user")

	);

	private final Map<String, String> users;
	private final Map<String, Set<String>> roles;
	private final boolean isEnabled;

	public TestCredentialValidator(Map<String, String> users, Map<String, Set<String>> roles) {
		this.users = users;
		this.roles = roles;
		this.isEnabled = checkIfEnabled();
	}

	public TestCredentialValidator() {
		this(USERS, ROLES);
	}

	protected boolean checkIfEnabled() {
		// Load properties from this project
		ExpressionEvaluator evaluator = new ELExpressionEvaluator();
		ConfigurableEnvironment environment = new ConfigurableEnvironment(new String[] { "application", "security" }, evaluator);

		String testUsersEnabled = environment
				.getProperty(TEST_USERS_ENABLED_PROPERTY, TEST_USERS_ENABLED_PROPERTY, "false");
		return Boolean.valueOf(testUsersEnabled);
	}

	@Override
	public boolean appliesTo(Credential credential) {
		return isEnabled && credential instanceof UsernamePasswordCredential;
	}

	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

		String password = users.get(login.getCaller());
		if (password != null && password.equals(login.getPasswordAsString())) {
			return new CredentialValidationResult(login.getCaller(), roles.get(login.getCaller()));
		} else if (password != null && !password.equals(login.getPasswordAsString())) {
			return CredentialValidationResult.INVALID_RESULT;
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
	}

}
