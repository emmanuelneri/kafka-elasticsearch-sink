FROM openjdk:16-alpine
VOLUME /tmp
COPY target/producer-*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]