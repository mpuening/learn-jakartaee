package io.github.learnjakartaee.security.identity;

import java.util.Map;
import java.util.Properties;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelConfiguredLDAPCredentialValidator extends LDAPCredentialValidator {

	public SpelConfiguredLDAPCredentialValidator(
			String ldapUrl,
			String userBaseDn,
			String userNameAttribute,
			String groupBaseDn,
			String groupMemberAttribute,
			String groupNameAttribute) {
		super(
				new SpelContext().evaluateExpression("ldapUrl", ldapUrl),
				new SpelContext().evaluateExpression("userBaseDn", userBaseDn),
				new SpelContext().evaluateExpression("userNameAttribute", userNameAttribute),
				new SpelContext().evaluateExpression("groupBaseDn", groupBaseDn),
				new SpelContext().evaluateExpression("groupMemberAttribute", groupMemberAttribute),
				new SpelContext().evaluateExpression("groupNameAttribute", groupNameAttribute));
	}

	/**
	 * These defaults match the learn-jakartaee-ldap-server project
	 */
	public SpelConfiguredLDAPCredentialValidator() {
		this(
				"env['LDAP_URL'] ?: properties['ldap.url'] ?: 'ldap://localhost:8389'",
				"env['LDAP_USER_BASE_DN'] ?: properties['ldap.user.basedn'] ?: 'ou=people,dc=example,dc=org'",
				"env['LDAP_USER_NAME_ATTR'] ?: properties['ldap.user.nameattr'] ?: 'uid'",
				"env['LDAP_GROUP_BASE_DN'] ?: properties['ldap.group.basedn'] ?: 'ou=groups,dc=example,dc=org'",
				"env['LDAP_GROUP_MEMBER_ATTR'] ?: properties['ldap.group.memberattr'] ?: 'uniqueMember'",
				"env['LDAP_GROUP_NAME_ATTR'] ?: properties['ldap.group.nameattr'] ?: 'cn'");
	}

	// ==========================================

	public static class SpelContext {

		public String evaluateExpression(String property, String expression) {
			// LOG.debug("Evaluate " + property + ": " + expression);
			ExpressionParser expressionParser = new SpelExpressionParser();
			Expression parsed = expressionParser.parseExpression(expression);

			EvaluationContext context = new StandardEvaluationContext(this);
			String value = String.valueOf(parsed.getValue(context));
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
