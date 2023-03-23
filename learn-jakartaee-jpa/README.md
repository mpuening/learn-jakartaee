Learn Jakarta EE APIs
=====================

## JPA Web App

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-jpa` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn -P liberty liberty:run
```

Once Liberty has started, open your browser to:

[http://localhost:9080/learn-jakartaee-jpa/index.jsp](http://localhost:9080/learn-jakartaee-jpa/index.jsp)

```
Note: Sometimes Liberty goes into a seemingly infinite loop with errors building the data source.
I do not know why. I just restart Liberty.
```

### WildFly

To start WildFly, run this command:

```
mvn -P wildfly cargo:run
```

Once WildFly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jpa/index.jsp](http://localhost:8080/learn-jakartaee-jpa/index.jsp)

### GlassFish

To start GlassFish, run this command:

```
mvn -P glassfish cargo:run
```

Once GlassFish has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jpa/index.jsp](http://localhost:8080/learn-jakartaee-jpa/index.jsp)

### TomEE

To start TomEE, run this command:

```
mvn -P tomee tomee:run
```

Once TomEE has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jpa/index.jsp](http://localhost:8080/learn-jakartaee-jpa/index.jsp)

## User Interface

From the `index.jsp` page, you can go to a page that adds people to the database.

## Configuration

The Data Source is configured in:

`src/main/java/io/github/learnjakartaee/config/DataSourceConfiguration.java`

The JNDI name defined in that class is used in:

```
src/main/resources/META-INF/persistence.xml
```

which connects to

```
PeopleService.java (@PersistenceContext)
```

While most app servers can use drivers bundled with the application, Liberty
must be configured from a global library for drivers in:

`src/main/liberty/config/server.xml`

The `server.xml` has a `derbypath` variable that is set in `pom.xml`

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-jpa .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-jpa || true && docker run -d -p 9080:9080 --name learn-jakartaee-jpa io.github.learnjakartaee/learn-jakartaee-jpa
```

