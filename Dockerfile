# Base image: Amazon Corretto 17
FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/YonseiDoorToDoor-0.0.1-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "/app/application.jar"]

EXPOSE 8080