FROM maven:4.0.0-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency: go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build/app/target/todo_app-0.0.1-SNAPSHOT.jar.

EXPOSE 8080

ENTRYPOINT ["java", "jar", "app//todo_app-0.0.1-SNAPSHOT.jar"]
