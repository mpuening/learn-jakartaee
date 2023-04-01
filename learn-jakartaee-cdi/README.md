Learn Jakarta EE APIs
=====================

## CDI versus Spring Comparison

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-cdi` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-cdi/index.html](http://localhost:9080/learn-jakartaee-cdi/index.html)

### WildFly

To start WildFly, run this command:

```
mvn -P wildfly cargo:run
```

Once WildFly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-cdi/index.html](http://localhost:8080/learn-jakartaee-cdi/index.html)

### GlassFish

To start GlassFish, run this command:

```
mvn -P glassfish cargo:run
```

Once GlassFish has started, open your browser to:

[http://localhost:8080/learn-jakartaee-cdi/index.html](http://localhost:8080/learn-jakartaee-cdi/index.html)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-cdi/index.html](http://localhost:8080/learn-jakartaee-cdi/index.html)

## User Interface

From the home page, you have simple links to:

* Test Application-scoped beans
* Test Request-scoped beans
* Test Session-scoped beans
* Test getting data using the controller/service/repository pattern
* Test saving data into the databae using JTA transactional methods

The controller/service/repository pattern implemented with JPA for both
CDI Beans and Spring Beans was a good exercise to complete.

## Configuration

This project os just a playground project to learn the CDI API.

In the `io.github.learnjakartaee.spring` package, one will find the usual config, controller,
service and respository packages and classes to get and save data from the database. This
code is used to compare how one uses CDI which one will find in `io.github.learnjakartaee.spring`
packages and classes. There is pretty much a one to one correspondence.

Liberty is configured from this file: `src/main/liberty/config/server.xml`. It uses the Jakarta EE web profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-cdi .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-cdi || true && docker run -d -p 9080:9080 --name learn-jakartaee-cdi io.github.learnjakartaee/learn-jakartaee-cdi
```
