FROM openjdk:20-ea-11
VOLUME /tmp
COPY build/libs/backend-1.0.0.jar comeon-backend.jar
ENTRYPOINT ["java", "-jar", "comeon-backend.jar"]
