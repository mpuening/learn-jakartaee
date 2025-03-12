package io.github.learnjakartaee.security.auth;

import java.security.Principal;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

import java.io.Serializable;

public final class SavedAuthentication implements Serializable {
	private static final long serialVersionUID = 6027357509249926910L;

	private final Principal principal;
	private final Set<String> groups;

	SavedAuthentication(final Principal principal, final Set<String> groups) {
		this.principal = principal;
		this.groups = unmodifiableSet(groups);
	}

	public Principal getPrincipal() {
		return principal;
	}

	public Set<String> getGroups() {
		return groups;
	}
}
