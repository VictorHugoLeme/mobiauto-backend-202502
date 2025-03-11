# Use a base image with Java installed
FROM amazoncorretto:21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR (Ensure you run `mvn clean package` before building)
COPY target/mobiauto-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
