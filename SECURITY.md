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
