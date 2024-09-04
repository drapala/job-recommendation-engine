FROM openjdk:17-jdk-alpine

LABEL authors="drapala"

WORKDIR /app

COPY ../build/libs/job-recommendation-engine-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
