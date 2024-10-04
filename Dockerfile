FROM openjdk:21-slim-bullseye

ARG ARTIFACT_NAME

ENV JAR_FILE=oauth2-authorization-server.jar

COPY /application/target/${ARTIFACT_NAME} ./

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dspring.profiles.active=${JAVA_ENVIRONMENT}", "/${JAR_FILE}"]