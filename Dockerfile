# Stage 1: build com Maven (usa o mvn instalado na imagem)
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# 1) Copia só o POM e já baixa as dependências em cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2) Copia o resto do código e empacota o JAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: runtime minimal com JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 3) Pega o JAR gerado no build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]