# Use the official OpenJDK base image for Java 21
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar to the container

COPY /build/libs /app/build

# Expose the port that your Spring Boot application will run on
EXPOSE 8500

# Specify the command to run on container startup
CMD ["java", "-jar", "/app/build/Kshirsa-v0.1.jar"]