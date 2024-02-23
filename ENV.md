Learn Jakarta EE Environment
============================

After a while developing Jakarta apps, I felt the need to have something similar to Spring Framework's Environment
class with profile support. CDI and properties just don't sit well with me. Sure, there is the Micro-profile Config API, but that does not work for a JSF application.

The `learn-jakartaee-env` project contains classes to support reading values from property files that
are also profile based similar to the Spring Framework. It also has rudimentary support for evaluating
property values as an expression. There are difference implementations for various types of expressions,
including:

* OGNL
* Spring Expression Language
* Micro-profile
* Expression Language

Instead of using the variable or property `SPRING_PROFILES_ACTIVE` and `spring.profiles.active`,
this library uses `APP_PROFILES_ACTIVE` and `app.profiles.active`.

Imagine two files:

`application.properties`

```
for=bar
```

and `application-unittest.properties`

```
for=test-bar
```

The following code, using the default profile, would return `bar`:

```
ExpressionEvaluator evaluator = new EnvExpressionEvaluator();
ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
String value = environment.getProperty("FooValue", "foo")); // bar
```

But if the `unittest` profile is set, another value is retrieved:

```
System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unitttest");

ExpressionEvaluator evaluator = new EnvExpressionEvaluator();
ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
String value = environment.getProperty("FooValue", "foo")); // test-bar
```

Values can also be expressions to pull values from environment variables or system properties.
How the expressions are evaluated depends on the implementation of the Environment. The following
is an example using Jakarta Expression Language;

`application.properties`

```
for=EVAL(not empty env.get('FOO') ? env.get('FOO') : properties.getOrDefault('foo', 'foo-default-value'))
```

NOTE that the expressions begins with `EVAL(` and ends with `)`


The following code shows getting the value of the above using an EL Environment:

```
ExpressionEvaluator evaluator = new ELExpressionEvaluator();
ConfigurableEnvironment environment = new ConfigurableEnvironment(evaluator);
String value = environment.getProperty("FooValue", "foo")); 
```

It isn't as powerful as Spring's implementation, but it solves the problems I have.

Check out the test cases for more examples.

