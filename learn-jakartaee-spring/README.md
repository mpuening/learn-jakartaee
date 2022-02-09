Learn Jakarta EE APIs
=====================

## Spring Application (without Spring Boot)

After building the application, you can run this application with Open Liberty or
Tom EE (via Cargo) using these commands from this `learn-jakartaee-spring` directory:

```
mvn -f pom-liberty.xml liberty:create
mvn -f pom-liberty.xml liberty:deploy
mvn -f pom-liberty.xml liberty:start

mvn -f pom-liberty.xml liberty:stop (when done testing)

mvn cargo:run
```

Once Liberty has started open your browser to [/](http://localhost:9080/learn-jakartaee-spring-jakartaee9)

```
Note: Because this project uses the Eclipse Transformer, the actual WAR file to deploy is the 'jakartaee9'
classifier version. However, the Open Liberty plug-in does not seem to have any support for classifiers.
In addition, there is no longer any way to directly deploy a named WAR file. So I try a trick and created
a pom-liberty.xml file that represents the jakaraee9 classifier named WAR file. But the liberty:run command
of the plug-in strangely seems to always run the assembly plug-in needlessly and this produces an empty WAR 
file. However, sticking with the liberty:start command, we can avoid this. I will continue to investigate
how best to incorporate the Eclipse Transformer into development.
```

From that page, you will see a message.

If using Cargo, open your browser to [/](http://localhost:8080/learn-jakartaee-spring)

Although this application does not do very much, it shows how much code one needs to 
boot up an application that supports properties and thymeleaf, just a couple of things 
that many developers now expect to get for free from Spring Boot. 

The boot process begins in the `AppInitializer` class which references the other Spring
configuration classes.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## Docker

To build a Docker image that uses Open Liberty, run this command:

```
mvn clean package && sudo docker build -t io.github.learnjakartaee/learn-jakartaee-spring .
```

To run the Docker image, run this command:

```
docker rm -f learn-jakartaee-spring || true && docker run -d -p 9080:9080 --name learn-jakartaee-spring io.github.learnjakartaee/learn-jakartaee-spring
```
