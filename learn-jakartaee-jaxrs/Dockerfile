FROM openliberty/open-liberty:22.0.0.1-full-java11-openj9-ubi

COPY --chown=1001:0 target/learn-jakartaee-jaxrs-0.0.1-SNAPSHOT.war /config/dropins/learn-jakartaee-jaxrs.war
COPY --chown=1001:0 src/main/liberty/config/server-docker.xml /config/server.xml

RUN configure.sh
