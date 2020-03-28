Learn Jakarta EE APIs
=====================

This project is about learning the Jakarta EE APIs (i.e. Java development without Spring).

Here is the main web site for [Jakarta EE](https://jakarta.ee/).

The projects in this repository focus on different areas, and some take inspiration
from the [Open Liberty Guides](https://openliberty.io/guides/).

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

This project is a super simple REST API containing just a ping service. But at least 
it shows how Swagger UI is built-in to Open Liberty.

## [`learn-jakartaee-struts`](./learn-jakartaee-struts)

This project is an Apache Struts 1.3 application. Even though Apache Struts is no longer 
active, it is amazing to me how capable it still is to creating a web application (except
for the security risks). This application features:

1. EJB back-end
2. XDoclet Generated Struts Configuration
3. Tiles Layout with navigation bar and side menus using Twitter Bootstrap CSS
4. Apache Validation (Validator) Rules

Building
========

This is a straight-forward maven project. Just run this command

```
mvn clean package
```

or

```
./mvnw clean package
```

Running
=======

See the README files in each project for more details.

