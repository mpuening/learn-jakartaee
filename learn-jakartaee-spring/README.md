Learn Jakarta EE APIs
=====================

## Spring Application (without Spring Boot)

After building the application, you can run this application with Open Liberty using 
this command from this `learn-jakartaee-spring` directory:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started open your browser to [/](http://localhost:9080/learn-jakartaee-spring)

From that page, you will see a message.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.
