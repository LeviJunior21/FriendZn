# syntax=docker/dockerfile:1.2
ARG DOCKER_BUILDKIT=0
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install -y wget unzip
RUN wget https://services.gradle.org/distributions/gradle-8.3-bin.zip -P /tmp
RUN unzip -d /opt/gradle gradle-8.3-bin.zip
ENV GRADLE_HOME=/opt/gradle/gradle-8.3
ENV PATH=${GRADLE_HOME}/bin:${PATH}

RUN ./gradle clean build

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/friendzone-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
