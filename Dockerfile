FROM openjdk:24-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package

FROM openjdk:24-jdk-slim AS runtime
WORKDIR /app
RUN mkdir -p /app/orders_data /app/order_service
COPY --from=builder /app/target/*.jar /app/order_service/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/order_service/app.jar"]
