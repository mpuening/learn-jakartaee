Learn Jakarta EE APIs
=====================

## Jakarta Server Faces

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-jsf` directory:

```
mvn liberty:stop && mvn liberty:run

mvn cargo:run
```

Once Liberty has started open your browser to [index.xhtml](http://localhost:9080/learn-jakartaee-jsf/index.xhtml)

From that page, you have a link to get a greeting.

If using Cargo, open your browser to [/](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)

```
Note: As I write this, TomEE 9.0.0-M7 does not yet support JSF 3.0, so it does not work yet.
```

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jsf .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jsf || true && docker run -d -p 9080:9080 --name learn-jakartaee-jsf io.github.learnjakartaee/learn-jakartaee-jsf
```

## TODO

How do I write a test case that does not require the server to be started?