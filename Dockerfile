# Use uma JRE do Eclipse Temurin (OpenJDK 17)
FROM eclipse-temurin:17-jre-jammy

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR empacotado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta que seu Spring Boot usa (padrão 8080)
EXPOSE 8080

# Comando para rodar sua aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
