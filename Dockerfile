# syntax=docker/dockerfile:1.2
ARG DOCKER_BUILDKIT=0
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk wget unzip

# Cria um diretório de trabalho no contêiner
WORKDIR /app

# Copia o código-fonte para o contêiner
COPY . .

# Baixa e instala o Gradle
RUN wget https://services.gradle.org/distributions/gradle-8.3-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-8.3-bin.zip && \
    rm -f /tmp/gradle-8.3-bin.zip

# Configura as variáveis de ambiente para o Gradle
ENV GRADLE_HOME=/opt/gradle/gradle-8.3
ENV PATH=${GRADLE_HOME}/bin:${PATH}

RUN chmod +x gradlew

# Compilação do projeto com o Gradle
RUN gradle clean build

FROM openjdk:17-jdk-slim

EXPOSE 8080

# Configura o diretório de trabalho no segundo estágio
WORKDIR /app

# Copia o arquivo JAR do estágio de construção
COPY --from=build /app/build/libs/friendzone-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar o aplicativo quando o contêiner inicia
ENTRYPOINT ["java", "-jar", "app.jar"]
