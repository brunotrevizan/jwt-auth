####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the container image run:
#
# ./mvnw package
#
# Then, build the image with:
#
# docker build -f docker/Dockerfile -t quarkus/api-prova-flutter .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/api-prova-flutter
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5005
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 quarkus/api-prova-flutter
#
# This image uses the `run-java.sh` script to run the application.
FROM registry.access.redhat.com/ubi9/ubi-minimal:9.1.0-1829

ARG JAVA_PACKAGE=java-17-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.8
ARG JEMALLOC_DOWNLOAD_URL=https://download-ib01.fedoraproject.org/pub/epel/8/Everything/x86_64/Packages/j/jemalloc-5.2.1-2.el8.x86_64.rpm

ENV TZ='America/Sao_Paulo' LANG='pt_BR.UTF-8' LANGUAGE='pt_BR:pt'

# Copy Language PT-BR
COPY docker/glibc-langpack-pt-2.34-40.el9_1.1.x86_64.rpm /tmp/langpack-pt.rpm

# Install java and the run-java script
# Also set up permissions for user `1001`
RUN rpm -vi /tmp/langpack-pt.rpm \
    && curl ${JEMALLOC_DOWNLOAD_URL} -o /tmp/jemalloc.rpm \
    && rpm -vi /tmp/jemalloc.rpm \
    && microdnf --setopt=install_weak_deps=0 --setopt=tsflags=nodocs install -y ca-certificates ${JAVA_PACKAGE} \
    && mkdir /deployments \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/conf/security/java.security \
    && rm /tmp/*.rpm \
    && microdnf clean all && [ ! -d /var/cache/yum ] || rm -rf /var/cache/yum

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --chown=1001 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=1001 target/quarkus-app/*.jar /deployments/
COPY --chown=1001 target/quarkus-app/app/ /deployments/app/
COPY --chown=1001 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 1001
ENV LD_PRELOAD="/usr/lib64/libjemalloc.so.2"
ENV MALLOC_CONF="narenas:2"
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT ["/deployments/run-java.sh"]