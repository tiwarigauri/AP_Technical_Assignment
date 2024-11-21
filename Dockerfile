# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from your target directory to the container
COPY target/authservice-0.0.1-SNAPSHOT.jar /app/authservice-0.0.1-SNAPSHOT.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "authservice-0.0.1-SNAPSHOT.jar"]
