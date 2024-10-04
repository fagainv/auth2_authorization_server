FROM openjdk:21-slim-bullseye

ARG ARTIFACT_NAME

RUN echo "The value of ARTIFACT_NAME is: ${ARTIFACT_NAME}"

ENV JAR_FILE=oauth2-authorization-server.jar

RUN echo "The value of JAR_FILE is: ${JAR_FILE}"

COPY /application/target/${ARTIFACT_NAME} ./

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dspring.profiles.active=${JAVA_ENVIRONMENT}", "/oauth2-authorization-server.jar"]