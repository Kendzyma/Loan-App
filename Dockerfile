
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/loan.jar loan.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "loan.jar"]