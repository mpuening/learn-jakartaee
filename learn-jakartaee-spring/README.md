Learn Jakarta EE APIs
=====================

## Spring Web App (without Spring Boot)

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-spring` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-spring/](http://localhost:9080/learn-jakartaee-spring/)

### Wildfly

To start Wildfly, run this command:

```
mvn -P wildfly cargo:run
```

Once Wildfly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-spring](http://localhost:8080/learn-jakartaee-spring)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-spring](http://localhost:8080/learn-jakartaee-spring)

## User Interface

From the home page, you will see a message.

Although this application does not do very much, it shows how much code one needs to 
boot up an application that supports properties and thymeleaf, just a couple of things 
that many developers now expect to get for free from Spring Boot. 

The boot process begins in the `AppInitializer` class which references the other Spring
configuration classes.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-spring .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-spring || true && docker run -d -p 9080:9080 --name learn-jakartaee-spring io.github.learnjakartaee/learn-jakartaee-spring
```
