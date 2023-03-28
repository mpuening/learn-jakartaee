FROM openliberty/open-liberty:22.0.0.1-full-java11-openj9-ubi

COPY --chown=1001:0 target/learn-jakartaee-jaxws-server-0.0.1-SNAPSHOT.war /config/dropins/learn-jakartaee-jaxws-server.war
COPY --chown=1001:0 src/main/liberty/config/server-docker.xml /config/server.xml

ARG REPO_DIR="target/learn-jakartaee-jaxws-server-0.0.1-SNAPSHOT/WEB-INF/lib"
ARG DERBY_VERSION="10.14.2.0"
COPY --chown=1001:0 $REPO_DIR/derby-$DERBY_VERSION.jar /config/derby.jar

RUN configure.sh
