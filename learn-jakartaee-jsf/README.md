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

### Wildfly

To start Wildfly, run this command:

```
mvn -P wildfly cargo:run
```

Once Wildfly has started, open your browser to:

[http://localhost:8080/learn-jakartaee-jsf/index.xhtml](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)

However, you will notice that logging in fails:

```
Wildfly needs a tweak to its JASPI settings to work with this example project that uses an
in-memory data store of application users,
```

Go ahead and stop it with Ctrl-C.

Next run the following command to set up a manager account (note your version Wildfly that was downloaded):

```
sh ./target/cargo/installs/wildfly-27.0.1.Final/wildfly-27.0.1.Final/bin/add-user.sh
```

Using the menu options to create a Mgmt User with these credentials: mgr/password1.

Now re-run the application server with this command:

```
mvn -P wildfly cargo:run
```

Now open the administration console at:

[http://localhost:9990/](http://localhost:9990/)

And log in with the `mgr` user that you created above.

In the admin app, navigate to Configuration > Subsystems > Web > Application Security Domain > Other > View

Edit and change the value of `Enable JASPI` to `ON` and `Integrated JASPI` to `OFF`, then click Save.

To finish the configuration change, click the `Reload Required` link in the top banner menu bar to reload the server.

Finally, re-open your browser to:

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
