Learn Jakarta EE APIs
=====================

## JAX-RS

After building the application, you can run this application with Open Liberty using 
this command from this `learn-jakartaee-jaxrs` directory:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started open your browser to [index.html](http://localhost:9080/index.html)

From that page, you have three simple links to:

1. [Ping the server](http://localhost:9080/api/ping)
2. [View the OpenAPI Spec](http://localhost:9080/openapi)
3. [Use Swagger UI](http://localhost:9080/openapi/ui)

The `ping` service is implemented in the `PingService` class and the `/api`
context path is controlled by the `JAXRSConfiguration`.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Micro-profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jaxrs .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jaxrs || true && docker run -d -p 9080:9080 --name learn-jakartaee-jaxrs io.github.learnjakartaee/learn-jakartaee-jaxrs
```

## TODO

How do I write a test case that does not require the server to be started?
