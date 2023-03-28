Learn Jakarta EE APIs
=====================

## JAX-WS Web Services

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-jaxws-server` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, run the main program in the `learn-jakartaee-jaxws-client` project to invoke
the web service. For liberty, make sure to change the port number to 9080.

### WildFly

To start WildFly, run this command:

```
mvn -P wildfly cargo:run
```

Once WildFly has started, run the main program in the `learn-jakartaee-jaxws-client` project to invoke
the web service.

### GlassFish

To start GlassFish, run this command:

```
mvn -P glassfish cargo:run
```

Once GlassFish has started, run the main program in the `learn-jakartaee-jaxws-client` project to invoke
the web service.

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, run the main program in the `learn-jakartaee-jaxws-client` project to invoke
the web service.

## Configuration

The web service is implemented in:

`src/main/java/io/github/learnjakartaee/service/AircraftService.java`

The URL end point is defined in:

```
src/main/webapp/WEB-INF/web.xml
```

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jaxws-server .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jaxws-server || true && docker run -d -p 9080:9080 --name learn-jakartaee-jaxws-server io.github.learnjakartaee/learn-jakartaee-jaxws-server
```

