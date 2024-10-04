FROM openjdk:21-slim-bullseye

ARG ARTIFACT_NAME

RUN echo "The value of ARTIFACT_NAME is: ${ARTIFACT_NAME}"

ENV JAR_FILE=${ARTIFACT_NAME}

RUN echo "The value of JAR_FILE is: ${JAR_FILE}"

COPY /application/target/${ARTIFACT_NAME} ./

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "echo The value of JAR_FILE in the entrypoing: $JAR_FILE && java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$JAVA_ENVIRONMENT /oauth2-authorization-server.jar"]