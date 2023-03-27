Learn Jakarta EE APIs
=====================

[![Continuous Integration](https://github.com/mpuening/learn-jakartaee/actions/workflows/ci.yml/badge.svg)](https://github.com/mpuening/learn-jakartaee/actions/workflows/ci.yml)

This project is about learning the Jakarta EE APIs (i.e. Java development without Spring Boot).

Here is the main web site for [Jakarta EE](https://jakarta.ee/). The specifications 
can be found [here](https://jakarta.ee/specifications/).

The projects in this repository focus on different areas, and some take inspiration
from the [Open Liberty Guides](https://openliberty.io/guides/).

NOTE! If you are looking for examples of Jakarta EE 8, 
[click here](https://github.com/mpuening/learn-jakartaee/tree/jakartaee8).

So what is in this project?
===========================

Here are the sub projects:

## [`learn-jakartaee-jsp`](./learn-jakartaee-jsp)

This project contains simple Servlet and JSP app. It is just a simple demo of a servlet 
that supports GET and POST.

## [`learn-jakartaee-jpa`](./learn-jakartaee-jpa)

This project focuses on accessing a database using JPA. The user interface allows one 
to store new records in the database.

## [`learn-jakartaee-jaxrs`](./learn-jakartaee-jaxrs)

This Microprofile 5 project is a super simple REST API containing just a ping service.
But at least it shows how Swagger UI is built-in to Open Liberty.

## [`learn-jakartaee-struts`](./learn-jakartaee-struts)

This project is an Apache Struts 1.3 application. Even though Apache Struts is no longer 
active, it is amazing to me how capable it still is to creating a web application (except
for the security risks). This application features:

1. EJB back-end (See [`learn-jakartaee-ejb`](./learn-jakartaee-ejb))
2. XDoclet Generated Struts Configuration
3. Tiles Layout with navigation bar and side menus using Twitter Bootstrap CSS
4. Apache Validation (Validator) Rules

## [`learn-jakartaee-jsf`](./learn-jakartaee-jsf)

This project is similar to the Struts application, except that it uses Jakarta Server 
Faces. Likewise, it features:

1. EJB back-end (See [`learn-jakartaee-ejb`](./learn-jakartaee-ejb))
2. Authentication and URL Authorization
3. Layout with navigation bar and side menus using Twitter Bootstrap CSS
4. JSF Form Validation

## [`learn-jakartaee-spring`](./learn-jakartaee-spring)

I did not want to include an example application that uses Spring, but in a conversation 
with someone about these examples, this person seemed to forget that much of Spring relies 
on the Jakarta EE APIs, and still how easy it is to configure an application without 
Spring Boot. The `AppInitializer` is discovered by the `SpringServletContainerInitializer`
which itself is an instance of `ServletContainerInitializer` which is part of the Jakarta 
EE API. It's actually quite simple.

## [`learn-jakartaee-legacy-support`](./learn-jakartaee-legacy-support)

With the release of Jakarta EE 9, there will be old frameworks, such as Struts, that no longer work
on the modern app servers. However, the Eclipse Transformer can breathe new life into old frameworks.
The projects under the `learn-jakarta-legacy-support` folder address old frameworks to make a version that
is compatible with modern application servers. Each project is fairly straightforward. An old JAR file
is unzipped, then the Eclipse Transformer is run to create a `jakarta` classifier version of the JAR file
that uses the new name space. Only a simple `pom.xml` is needed per dependency. No new Java code is
needed.

## [`learn-jakartaee-test`](./learn-jakartaee-test)

I chose to use Apache TomEE/OpenEJB embedded server for the test cases. NO app server installation or
remote test cases needed. There are three sub-projects. The first is the simplest for EJBs which just uses
OpenEJB. The two other are used for micro-profile and web profile projects. These projects clean up
old dependencies and add additional support forEmbedded TomEE that is not included by default, for example
security and micro-profile support.

Building
========

This is a straight-forward maven project. Just run this command:

```
mvn clean install
```

or

```
./mvnw clean install
```

The install phase is needed because the legacy projects need to install classified builds
in order to be used by the other projects.

Running
=======

See the README.md files in each project for more details.

Is the age of the App Server over?
==================================

The idea of the app server is good. Developers write code to a well defined specification and
can hand the application to an operations team to be deployed. It was supposed to be a very
smooth and easy process. Of course, we all know that is not what happened. In reality,
app server vendors interpreted the specification differently and/or added non-standard features
that essentially made every app server have a personality of their own.

At one time, all Java developers knew about BEA WebLogic, JBoss, and IBM WebSphere and more.
And then this framework called Spring came along. It had a different take on web development. At
first, it was friendly with app servers. Then developers realized that they could get away
just running with a simple servlet container like Tomcat. And when Spring Boot came along
with embedded Tomcat, the classic app servers became slow moving dinosaurs.

Again, the idea of the aoo server is good. Except as I write this today, the specification that
developers use is the Docker Container. And the operations team run Kubernetes clusters. No
one speaks of WebLogic, JBoss, or WebSphere anymore. So, is the age of the app server over?
Not if you consider the Docker solution. Is the age of the *Java* app server over? I would say
yes. I don't know anyone who uses them anymore.

But I am excited about the future of Jakarta EE. I look forward to how Java records are incorporated
into specifications such as JPA. Also, how will Project Loom affect JAX-RS and the Servlet API? It is
these changes why I wanted this project.

Running and Testing Jakarta EE Applications
===========================================

When app servers became widespread, the notion of continuous unit testing was not as widespread.
And it is no secret that the Jakarta EE specifications did not have testing front and center
when designed. And still today, unit testing Jakarta EE code is not trivial as I think it should be.
And I did want this project to have unit testing. I have heard of the Arqullian project for a long
time now, but never had a project to use it on till this project. But wow! Setting up unit test
cases was *not* easy. Arquillian advertises itself as an easy way to write test cases, but I had all
sorts of problems. Sure I found many examples that tested "Hello World" apps, but when I set up
my apps with that configuration, problems came up. But I persevered and conquered all the problems
I faced. For example, I have an app that uses a TLD file. Even today, Arquillian ships with a dependency
on xerces.... Xerces? really? Ok, delete. And then I had all sorts of problems with the MyFaces
implementation that TomEE Arquillian project comes with. Despite a lot of time I invested trying
to make my test cases work with MyFaces, in the end I just deleted the its implementation and inserted
the Mojarra implementation. I still wonder what I am doing wrong if anything at all. When I google
errors, I often get results from 10+ years ago or it tells me "It looks like there aren't many great
matches for your search." Wow. This tells me not many people are deveoping Jakarta EE apps with Arquillian.

For each of my projects I wanted to an easy way to start the app and run test cases. I most certainly
did not want anyone to separately download, run, and set up an app server. A simple maven command
should build and run the applications. Developers should be able to import this project into their
favorite IDE and run test cases. To run the web applications, one can choose on of the following
commands:

```
mvn -P liberty liberty:run
mvn -P wildfly cargo:run
mvn -P glassfish cargo:run
mvn -P tomee tomee:run
```

The various maven plugins (liberty, cargo, tomee) are used to perform the download of the app
server and then deploy the apps. The maven plugin configuration is all included in the parent
`pom.xml` file.

Running the test cases is almost just as easy. Again, no separate app server is required to
be used. The test cases use TomEE as the embedded app server. So it is really easy to debug
code. The only trick (at least within Eclipse) is to set these VM arguments:

```
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED
```

I'll continue to investigate module settings. I am not well versed with the Java module system yet.


TODO
====

Ideas
* learn-jakartaee-jpa-ejb
* learn-jakartaer-ldap-server
* learn-jakartaee-auth-server
* learn-jakartaee-jaxws
* learn-jakartaee-jms
* YAML Config

Learn from
* https://github.com/OpenLiberty/liberty-bikes and https://openliberty.io/blog/2021/09/24/liberty-bikes.html
* https://hantsy.gitbook.io/java-ee-8-by-example/
* https://github.com/phillip-kruger/microprofile-demo
* https://github.com/apache/tomee/tree/main/examples
