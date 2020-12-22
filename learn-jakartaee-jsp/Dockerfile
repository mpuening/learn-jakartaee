FROM openliberty/open-liberty:21.0.0.1-beta-full-java11-openj9-ubi

COPY --chown=1001:0 target/learn-jakartaee-jsp-0.0.1-SNAPSHOT.war /config/dropins/learn-jakartaee-jsp.war
COPY --chown=1001:0 src/main/liberty/config/server-docker.xml /config/server.xml

RUN configure.sh
