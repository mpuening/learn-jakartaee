Learn Jakarta EE APIs
=====================

## JPA

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-jpa` directory:

```
mvn liberty:stop && mvn liberty:run

mvn cargo:run
```

Once Liberty has started open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-jpa/index.jsp)

```
Note: Sometimes Liberty goes into a seemingly infinite loop with errors building the data source.
I do not know why. I just restart Liberty.
```

If using Cargo, open your browser to [index.jsp](http://localhost:8080/learn-jakartaee-jpa/index.jsp)

From that `index.jsp` page, you can go to a page that adds people to the database.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web Profile with JNDI support. The database connection information
is in this file. You will find that the `jdbc/personDataSource` connects to:

```
src/main/webapp/WEB-INF/ibm-bnd-web.xml
```

or

```
src/main/webapp/WEB-INF/resources.xml (TomEE)
```
 
which connects to

```
src/main/webapp/WEB-INF/web.xml
```

 which connects to

```
src/main/resources/META-INF/persistence.xml
```

which connects to

```
PeopleService.java (@PersistenceContext)
```

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
## TODO

How do I write a test case that does not require the server to be started?
