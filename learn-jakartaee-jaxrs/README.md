Learn Jakarta EE APIs
=====================

## JAX-RS Web API

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-jaxrs` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-jaxrs/index.html](http://localhost:9080/learn-jakartaee-jaxrs/index.html)

### Wildfly

To start Wildfly, run this command:

```
mvn -P wildfly cargo:run
```

Once Wildfly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jaxrs/index.html](http://localhost:8080/learn-jakartaee-jaxrs/index.html)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jaxrs/index.html](http://localhost:8080/learn-jakartaee-jaxrs/index.html)

## User Interface

From the Open Liberty home page, you have three simple links to:

1. [Ping the server](http://localhost:9080/learn-jakartaee-jaxrs/api/ping)
2. [View the OpenAPI Spec](http://localhost:9080/openapi)
3. [Use Swagger UI](http://localhost:9080/openapi/ui)

The `ping` service is implemented in the `PingService` class and the `/api`
context path is controlled by the `JAXRSConfiguration`.

Wildfly and TomEE do not support the Open API and Swagger UI links.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`. It uses the Jakarta EE Micro-profile.

The OpenAPI YAML file is located in `src/webapp/META-INF` folder.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jaxrs .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jaxrs || true && docker run -d -p 9080:9080 --name learn-jakartaee-jaxrs io.github.learnjakartaee/learn-jakartaee-jaxrs
```
