FROM openjdk:21-slim-bullseye

ARG ARTIFACT_NAME

ENV ARTIFACT_NAME=${ARTIFACT_NAME}

COPY /application/target/${ARTIFACT_NAME} ./

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dspring.profiles.active=${JAVA_ENVIRONMENT}", "/${ARTIFACT_NAME}"]