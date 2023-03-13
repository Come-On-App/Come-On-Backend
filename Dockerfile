FROM openjdk:20-ea-11
VOLUME /tmp
COPY build/libs/backend-0.0.1-SNAPSHOT.jar comeon-backend.jar
ENTRYPOINT ["java", "-jar", "comeon-backend.jar"]
