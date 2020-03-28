Learn Jakarta EE APIs
=====================

## Struts

After building the application, you can run this application with Open Liberty using 
this command from this `learn-jakartaee-struts` directory:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-struts/index.jsp)

From that page, you have a page with a navigation bar along the top. The main section 
of the application has a "People" page to add people to the system. The `PeopleAction`
class uses the `PeopleService` EJB to manage people.

There are other place holder pages that show how one can implement a navigation bar 
and side menus.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

XDoclet is configured in `pom.xml` and requires that the Struts classes be named `*Action.class` 
and `*Form.class` respectively. Note that XDoclet can not understand Java 5 syntax.

Validation is accomplished by XDoclet annotations in the form beans that build `validation.xml`
rules. 

The Tiles layout is defined in `tiles-def.xml`. The layout JSPs are in the `layout` 
directory under the `WEB-INF` directory. `Webjars` are used to get support for `JQuery` 
and `Bootstrap`.

## TODO

How do I write a test case that does not require the server to be started?

I should work more on exception handling.

