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

## Alternative DataSource Configuration

In persistence.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
	version="3.0">
 
	<persistence-unit name="jdbcPersonDataSource" transaction-type="JTA">
		<provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
		<properties>
			<property name="jakarta.persistence.jdbc.driver"
				value="org.apache.derby.jdbc.EmbeddedDriver" />
			<property name="jakarta.persistence.jdbc.url"
				value="jdbc:derby:memory:persondb;create=true" />

			<property name="eclipselink.logging.level" value="INFO" />
			<property name="eclipselink.target-database" value="DERBY" />
			<property name="eclipselink.ddl-generation"
				value="drop-and-create-tables" />
		</properties>
	</persistence-unit>
	
</persistence>
```

Or in DataSourceConfiguration.java

```
package io.github.learnjakartaee.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Startup;

/**
 * If using, consider using ${ENV=DATABASE_USER} / ${ENV=DATABASE_PASSWORD}
 */
@DataSourceDefinition(
 	name = "jdbc/personDataSource",
 	className = "org.apache.derby.jdbc.EmbeddedDataSource",
 	url = "jdbc:derby:memory:persondb;create=true",
	user = "APP",
	password = "",
	initialPoolSize = 1,
	minPoolSize = 1
)
@Startup
public class DataSourceConfiguration {
}
```