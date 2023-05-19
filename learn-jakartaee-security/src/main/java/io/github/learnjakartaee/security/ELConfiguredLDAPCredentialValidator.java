package io.github.learnjakartaee.security;

import java.util.Map;
import java.util.Properties;

import jakarta.el.ELProcessor;

public class ELConfiguredLDAPCredentialValidator extends LDAPCredentialValidator {

	public ELConfiguredLDAPCredentialValidator(
			String ldapUrl,
			String userBaseDn,
			String userNameAttribute,
			String groupBaseDn,
			String groupMemberAttribute,
			String groupNameAttribute) {
		super(
				new ELContext().evaluateExpression("ldapUrl", ldapUrl),
				new ELContext().evaluateExpression("userBaseDn", userBaseDn),
				new ELContext().evaluateExpression("userNameAttribute", userNameAttribute),
				new ELContext().evaluateExpression("groupBaseDn", groupBaseDn),
				new ELContext().evaluateExpression("groupMemberAttribute", groupMemberAttribute),
				new ELContext().evaluateExpression("groupNameAttribute", groupNameAttribute));
	}

	/**
	 * These defaults match the learn-jakartaee-ldap-server project
	 */
	public ELConfiguredLDAPCredentialValidator() {
		this(
				"not empty env.get('LDAP_URL') ? env.get('LDAP_URL') : "
						+ "properties.getOrDefault('ldap.url', 'ldap://localhost:8389')",

				"not empty env.get('LDAP_USER_BASE_DN') ? env.get('LDAP_USER_BASE_DN') : "
						+ "properties.getOrDefault('ldap.user.basedn', 'ou=people,dc=example,dc=org')",

				"not empty env.get('LDAP_USER_NAME_ATTR') ? env.get('LDAP_USER_NAME_ATTR') : "
						+ "properties.getOrDefault('ldap.user.nameattr', 'uid')",

				"not empty env.get('LDAP_GROUP_BASE_DN') ? env.get('LDAP_GROUP_BASE_DN') : "
						+ "properties.getOrDefault('ldap.group.basedn', 'ou=groups,dc=example,dc=org')",

				"not empty env.get('LDAP_GROUP_MEMBER_ATTR') ? env.get('LDAP_GROUP_MEMBER_ATTR') : "
						+ "properties.getOrDefault('ldap.group.memberattr', 'uniqueMember')",

				"not empty env.get('LDAP_GROUP_NAME_ATTR') ? env.get('LDAP_GROUP_NAME_ATTR') : "
						+ "properties.getOrDefault('ldap.group.nameattr', 'cn')");
	}

	// ==========================================

	public static class ELContext {

		public String evaluateExpression(String property, String expression) {
			// LOG.debug("Evaluate " + property + ": " + expression);
			ELProcessor processor = new ELProcessor();
			processor.defineBean("env", getEnv());
			processor.defineBean("properties", getProperties());
			String value = processor.eval(expression).toString();
			return value;
		}

		public Map<String, String> getEnv() {
			return System.getenv();
		}

		public Properties getProperties() {
			return System.getProperties();
		}
	}
}
