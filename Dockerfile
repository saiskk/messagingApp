# Stage 1: Build the application
FROM openjdk:17.0.2-slim-buster AS build
WORKDIR /messaging
COPY . .
RUN ./mvnw clean install

# Stage 2: Create a lightweight image to run the application
FROM openjdk:17.0.2-slim-buster
WORKDIR /messaging
COPY --from=build /app/target/messaging-0.0.1-SNAPSHOT.jar ./messaging.jar
EXPOSE 8080
CMD ["java", "-jar", "messaging.jar"]