FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-ubi9-minimal
WORKDIR /app
COPY --from=builder /app/target/jira-1.0.jar jira.jar
COPY ./resources ./resources

EXPOSE 8080
CMD ["java", "-jar", "jira.jar"]

