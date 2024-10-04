FROM openjdk:22-ea-21-slim-bullseye

COPY /application/target/${ARTIFACT_NAME} ./

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dspring.profiles.active=${JAVA_ENVIRONMENT}", "/${ARTIFACT_NAME}"]