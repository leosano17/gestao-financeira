FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean install
EXPOSE 8080
CMD ["java", "-jar", "target/gestao-financeira-0.0.1-SNAPSHOT.jar"]