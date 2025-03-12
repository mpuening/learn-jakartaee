Learn Jakarta EE Security
=========================

*Note* This documentation needs to be improved. The TomEE project is still
broken in the EAR deployment.

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

There are several "built-in" ways to initiate authentication:

* @BasicAuthenticationMechanismDefinition
* @FormAuthenticationMechanismDefinition
* @CustomFormAuthenticationMechanismDefinition
* @OpenIdAuthenticationMechanismDefinition

```
@CustomFormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue(
				loginPage = "/views/auth/login.xhtml", 
				errorPage = "/views/auth/login.xhtml?error"))
@ApplicationScoped
public class SecurityConfiguration {

}
```

One can also specify a custom mechanism by implementing the *HttpAuthenticationMechanism*.

However, there is a problem....

## Problem

Not until Jakarta EE 11 will there be a way to control what happens when multiple
authentication mechanisms are present within an application. As of now (Jakarta EE 10 and
before as I write this), the specification does not define what to do about multiple 
mechanisms. Here is the issue:

https://github.com/jakartaee/security/issues/188

This affects this application in the EAR project. The EAR project is where multiple
mechanisms come into play: the custom form based authentication of the UIs, the basic
authentications of the APIs.

## Solution

This project has one *HttpAuthenticationMechanism* to rule them all as seen in
*HttpAuthenticationMechanismChain*. Each application adds it own requirements to the chain
keyed by the application ServletContext's context path.
 


