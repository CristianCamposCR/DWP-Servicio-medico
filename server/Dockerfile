# Use an official Maven image as a parent image
FROM maven:3.9.5-amazoncorretto-17 as builder
# Set the working directory
WORKDIR /app
# Copy the pom.xml file
COPY pom.xml .
# Copy the source code
COPY src ./src
# Remeber set your properties

# Build the application
RUN mvn clean package -DskipTests
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17.0.2-jdk
# Copy the JAR file from the builder stage to the new image
COPY --from=builder /app/target/server-0.0.1-SNAPSHOT.jar /app.jar
# Expose the port the app runs in
EXPOSE 8080
# Run the JAR file
CMD ["java", "-jar", "app.jar"]