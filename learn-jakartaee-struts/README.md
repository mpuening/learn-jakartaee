Learn Jakarta EE APIs
=====================

## Struts

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-struts` directory:

```
mvn -f pom-liberty.xml liberty:create
mvn -f pom-liberty.xml liberty:deploy
mvn -f pom-liberty.xml liberty:start

mvn -f pom-liberty.xml liberty:stop (when done testing)

mvn cargo:run
```

Once Liberty has started open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-struts-jakartaee9/index.jsp)

```
Note: Because this project uses the Eclipse Transformer, the actual WAR file to deploy is the 'jakartaee9'
classifier version. However, the Open Liberty plug-in does not seem to have any support for classifiers.
In addition, there is no longer any way to directly deploy a named WAR file. So I try a trick and created
a pom-liberty.xml file that represents the jakaraee9 classifier named WAR file. But the liberty:run command
of the plug-in strangely seems to always run the assembly plug-in needlessly and this produces an empty WAR 
file. However, sticking with the liberty:start command, we can avoid this. I will continue to investigate
how best to incorporate the Eclipse Transformer into development.
```

From that page, you have a page with a navigation bar along the top. The main section 
of the application has a "People" page to add people to the system. The `PeopleAction`
class uses the `PeopleService` EJB to manage people.

There are other place holder pages that show how one can implement a navigation bar 
and side menus.

If using Cargo, open your browser to [/](http://localhost:8080/learn-jakartaee-struts/index.jsp)

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

How do I write a test case that does not require the server to be started?

I should work more on exception handling.

