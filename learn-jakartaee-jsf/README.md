Learn Jakarta EE APIs
=====================

## Jakarta Server Faces

After building the application, you can run this application with Open Liberty using 
this command from this `learn-jakartaee-jsf` directory:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-jsf/index.xhtml)

From that page, you have a link to get a greeting.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## TODO

How do I write a test case that does not require the server to be started?