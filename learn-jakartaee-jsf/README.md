Learn Jakarta EE APIs
=====================

## Jakarta Server Faces

After building the application, you can run this application with various application servers using 
the following commands from this `learn-jakartaee-jsf` directory:

### Open Liberty

To start Open Liberty, run this command:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started, open your browser to [index.html](http://localhost:9080/learn-jakartaee-jsf/index.xhtml)

User accounts are located in `InMemoryIdentityStore.java`

### Wildfly

Wildfly needs a tweak to its JASPI settings to work with this example project that uses an
in-memory data store of application users,

First you need to download and start Wildfl using this command:

```
mvn cargo:run
```

then, you can stop it with Ctrl-C.

Next run the following command to set up a manager account:

```
sh ./target/cargo/installs/wildfly-preview-26.1.1.Final/wildfly-preview-26.1.1.Final/bin/add-user.sh
```

Using  mgr/password1 are fine credentials for this example.

Now re-run the application server with this command:

```
mvn cargo:run
```

Now open the administration console at http://localhost:9990/ and log in with the `mgr` user

In the admin app, navigate to Configuration > Subsystems > Web > Application Security Domain > Other > View

Edit and change the value of `Enable JASPI` to `ON` and `Integrated JASPI` to `OFF`, then click Save

To finish the configuration change, click the `Reload Required` link in the top banner menu bar to reload the server.

Finally, open your browser to [index.html](http://localhost:8080/learn-jakartaee-jsf/index.xhtml)


### TomEE

To start TomEE, run this command:

```
mvn tomee:run
```

Once TomEE has started, open your browser to [index.html](http://localhost:8080/learn-jakartaee-jsf-0.0.1-SNAPSHOT/index.xhtml)

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