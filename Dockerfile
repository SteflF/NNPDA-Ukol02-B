FROM openjdk:11
EXPOSE 8080
WORKDIR /app
COPY target/nnpd-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "nnpd-0.0.1-SNAPSHOT.jar" ]
