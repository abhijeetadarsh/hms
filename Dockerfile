# Use Java 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy maven files
COPY ./pom.xml ./
COPY ./mvnw .
COPY ./.mvn ./.mvn

# Copy source code
COPY ./src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/hms-0.0.1-SNAPSHOT.jar"]