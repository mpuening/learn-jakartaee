Learn Jakarta EE Security
=========================

There are several "built-in" ways to implement authentication:

* @LdapIdentityStoreDefinition
* @DatabaseIdentityStoreDefinition
* @RdbmsIdentityStoreDefinition
* @DataBaseIdentityStoreDefinition
* @RememberMeIdentityStoreDefinition

The above annotations should be included on an @ApplicationScoped CDI bean. While those
are niceties, a true solution should include an IdentityStore implementation, with
a different value for priority for each is more than more is needed. The problem
is that the attributes on the annotation do not support parameter values in a standard
way (i.e. references to another CDI bean.)

More information: https://developer.ibm.com/tutorials/j-javaee8-security-api-3/

The `learn-jakartaee-security` contains classes for test users and integration to an
LDAP system. These classes use Environment profile to make it easier to configure for
different environments. There is also a `DelegatingCredentialValidator` class to allow
one to hook up various validators depending on configuration. For example, an
application might define its `IdentityStore` as follows:

```
@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

	private CredentialValidator credentialValidator = new DelegatingCredentialValidator(
			new TestCredentialValidator(),
			new LDAPCredentialValidator());

	@Override
	public CredentialValidationResult validate(Credential credential) {
		return credentialValidator.validate(credential);
	}
}
```

If "Test Users" are enabled, then developers can easily log into the application
as various types of users. When disabled, LDAP integration is used. Test users
are enabled for `local` and `unittest` profiles.

