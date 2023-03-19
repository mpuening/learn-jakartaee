Learn Jakarta EE APIs
=====================

## JSF Web App

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-jsf` directory:


### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-jsf/index.xhtml](http://localhost:9080/learn-jakartaee-jsf/index.xhtml)

### WildFly

To start WildFly, run this command:

```
mvn -P wildfly cargo:run
```

Once WildFly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsf/index.xhtml](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)

### GlassFish

To start GlassFish, run this command:

```
mvn -P glassfish cargo:run
```

Once GLassFish has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsf/index.xhtml](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsf/index.xhtml](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)

## User Interface

This app implements security. User accounts are located in `InMemoryIdentityStore.java`

From the `index.xhtml` page, you can go to a page that adds people to the database.

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
