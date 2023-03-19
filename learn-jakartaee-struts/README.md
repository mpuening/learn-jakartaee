Learn Jakarta EE APIs
=====================

## Struts Web App

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-struts` directory:
### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-struts](http://localhost:9080/learn-jakartaee-struts)

### WildFly

To start WildFly, run this command:

```
mvn -P wildfly cargo:run
```

Once WildFly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-struts](http://localhost:8080/learn-jakartaee-struts)

### GlassFish

To start GlassFish, run this command:

```
mvn -P glassfish cargo:run
```

Once GlassFish has started, open your browser to:

[http://localhost:8080/learn-jakartaee-struts](http://localhost:8080/learn-jakartaee-struts)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-struts](http://localhost:8080/learn-jakartaee-struts)

## User Interface

From the home page, you have a page with a navigation bar along the top. The main section 
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

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-struts .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-struts || true && docker run -d -p 9080:9080 --name learn-jakartaee-struts io.github.learnjakartaee/learn-jakartaee-struts
```

## TODO

I should work more on exception handling.

