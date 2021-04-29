FROM openliberty/open-liberty:21.0.0.5-beta-full-java11-openj9-ubi

COPY --chown=1001:0 target/learn-jakartaee-spring-jakartaee9-0.0.1-SNAPSHOT.war /config/dropins/learn-jakartaee-spring.war
COPY --chown=1001:0 src/main/liberty/config/server-docker.xml /config/server.xml

RUN configure.sh
