FROM openjdk:24-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package

FROM openjdk:24-jdk-slim as runtime
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]