# Stage 1: Build the application
FROM adoptopenjdk:17-jdk-hotspot-bionic AS build
WORKDIR /messaging
COPY . .
RUN ./mvnw clean install

# Stage 2: Create a lightweight image to run the application
FROM adoptopenjdk:17-jre-hotspot-bionic
WORKDIR /messaging
COPY --from=build /app/target/messaging-0.0.1-SNAPSHOT.jar ./messaging.jar
EXPOSE 8080
CMD ["java", "-jar", "messaging.jar"]
