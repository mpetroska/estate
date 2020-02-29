FROM openjdk:8-jdk-alpine

WORKDIR /

ARG JAR_FILE=target/estate-1.0.jar

COPY ${JAR_FILE} estate.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar",  "estate.jar"]

