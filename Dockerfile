# Use a lightweight Java 17 JDK image
FROM eclipse-temurin:17-jdk-alpine

# Create a volume for temporary files
VOLUME /tmp

# Copy the built jar from target folder
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]