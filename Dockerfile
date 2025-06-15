# Stage 1: build com Maven
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Copia somente o que precisa para baixar dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Agora copia o restante e faz o package
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: runtime apenas com JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o JAR gerado no stage anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]