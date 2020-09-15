Learn Jakarta EE APIs
=====================

## JSP and Servlets

After building the application, you can run this application with Open Liberty using 
this command from this `learn-jakartaee-jsp` directory:

```
mvn liberty:stop && mvn liberty:run
```

Once Liberty has started, open your browser to [index.jsp](http://localhost:9080/learn-jakartaee-jsp/index.jsp)

From that page, you have a link to get a greeting.

The `HelloServlet` manages the GET and POST, and there is also a `TimestampTag` to 
show an example custom tag. The views also use a `layout.tag` file to handle a simple
page layout.

## Configuration

Liberty is configured from this file: `src/main/liberty/config/server.xml`

It uses the Jakarta EE Web-profile.

## TODO

How do I write a test case that does not require the server to be started?