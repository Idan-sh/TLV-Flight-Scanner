# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file into the container at /app
COPY target/TLV-Flight-Scanner-0.0.1-SNAPSHOT.jar /app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]