Learn Jakarta EE APIs
=====================

## JSP and Servlet Web App

After building the application, you can run this application with Open Liberty or
TomEE (via Cargo) using these commands from this `learn-jakartaee-jsp` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-jsp/index.jsp](http://localhost:9080/learn-jakartaee-jsp/index.jsp)

### Wildfly

To start Wildfly, run this command:

```
mvn -P wildfly cargo:run
```

Once Wildfly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsp/index.jsp](http://localhost:8080/learn-jakartaee-jsp/index.jsp)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsp/index.jsp](http://localhost:8080/learn-jakartaee-jsp/index.jsp)

## User Interface

From the home page, you have a link to get a greeting.

The `HelloServlet` manages the GET and POST, and there is also a `TimestampTag` to 
show an example custom tag. The views also use a `layout.tag` file to handle a simple
page layout.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jsp .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jsp || true && docker run -d -p 9080:9080 --name learn-jakartaee-jsp io.github.learnjakartaee/learn-jakartaee-jsp
```
