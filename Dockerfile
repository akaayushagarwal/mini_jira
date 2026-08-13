# Stage 1: Build the application using Java 26
FROM maven:3.9-eclipse-temurin-26 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Stage 2: Run the application using Java 26
FROM eclipse-temurin:26-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]