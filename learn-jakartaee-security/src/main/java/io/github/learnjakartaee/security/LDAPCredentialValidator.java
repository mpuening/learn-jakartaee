package io.github.learnjakartaee.security;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

/**
 * Credential validator
 */
public class LDAPCredentialValidator implements CredentialValidator {

	private final String ldapUrl;

	private final String userBaseDn;

	private final String userNameAttribute;

	private final String groupBaseDn;

	private final String groupMemberAttribute;

	private final String groupNameAttribute;

	public LDAPCredentialValidator(
			String ldapUrl,
			String userBaseDn,
			String userNameAttribute,
			String groupBaseDn,
			String groupMemberAttribute,
			String groupNameAttribute) {
		this.ldapUrl = ldapUrl;
		this.userBaseDn = userBaseDn;
		this.userNameAttribute = userNameAttribute;
		this.groupBaseDn = groupBaseDn;
		this.groupMemberAttribute = groupMemberAttribute;
		this.groupNameAttribute = groupNameAttribute;
	}

	public LDAPCredentialValidator(String ldapUrl, String userBaseDn, String groupBaseDn) {
		this(ldapUrl, userBaseDn, "uid", groupBaseDn, "uniqueMember", "cn");
	}

	@Override
	public boolean appliesTo(Credential credential) {
		return credential instanceof UsernamePasswordCredential;
	}

	@Override
	public CredentialValidationResult validate(Credential credential) {
		if (credential instanceof UsernamePasswordCredential) {
			return validate((UsernamePasswordCredential) credential);
		} else {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
	}

	/**
	 * Test username and password against LDAP
	 */
	protected CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {

		// Distinguished name (dn) of the user from user name attribute and baseDn
		String userDn = String.format("%s=%s,%s", userNameAttribute, usernamePasswordCredential.getCaller(),
				userBaseDn);

		LdapContext ldapContext = bindToLdap(ldapUrl, userDn,
				new String(usernamePasswordCredential.getPassword().getValue()));

		// If no context, then username/password is invalid
		if (ldapContext == null) {
			return CredentialValidationResult.INVALID_RESULT;
		}

		// Query groups
		List<SearchResult> groupSearchResults = searchGroups(ldapContext, userDn);

		// Collect the groups from the search results
		Set<String> groups = new LinkedHashSet<>();
		for (SearchResult searchResult : groupSearchResults) {
			for (Object group : extractAttribute(searchResult, groupNameAttribute)) {
				groups.add(group.toString());
			}
		}

		try {
			ldapContext.close();
		} catch (NamingException ignored) {
		}

		return new CredentialValidationResult(new CallerPrincipal(usernamePasswordCredential.getCaller()), groups);
	}

	/**
	 * Bind to LDAP using userDn and password. Returns null is user is invalid
	 */
	protected LdapContext bindToLdap(String url, String userDn, String password) {
		try {
			Hashtable<String, String> environment = new Hashtable<>();

			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, url);

			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PRINCIPAL, userDn);
			environment.put(Context.SECURITY_CREDENTIALS, password);

			return new InitialLdapContext(environment, null);
		} catch (AuthenticationException e) {
			return null;
		} catch (NamingException e) {
			throw new IllegalStateException("Naming Exception", e);
		}
	}

	/**
	 * Search groups (e.g. member={0} where member is groupMemberAttribute) from
	 * groupBaseDn where user is userDn
	 */
	protected List<SearchResult> searchGroups(LdapContext ldapContext, String userDn) {
		try {
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(new String[] { groupNameAttribute });
			return Collections.list(

					ldapContext.search(groupBaseDn,

							String.format("(%s={0})", groupMemberAttribute),

							new Object[] { userDn },

							controls));
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Extract LDAP attribute from ldap record
	 */
	protected List<?> extractAttribute(SearchResult searchResult, String attributeName) {
		try {
			return Collections.list(searchResult.getAttributes().get(attributeName).getAll());
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}
}
