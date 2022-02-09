Learn Jakarta EE APIs
=====================

## JSP and Servlets

After building the application, you can run this application with Open Liberty or
TomEE (via Cargo) using these commands from this `learn-jakartaee-jsp` directory:

```
mvn liberty:stop && mvn liberty:run

mvn cargo:run
```

Once Liberty has started, open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-jsp/index.jsp)

If using Cargo, open your browser to [index.jsp](http://localhost:8080/learn-jakartaee-jsp/index.jsp)

From that page, you have a link to get a greeting.

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

## TODO

How do I write a test case that does not require the server to be started?