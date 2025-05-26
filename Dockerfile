# Use uma imagem base com o JDK 17 já instalado
FROM openjdk:17-jdk-slim

# Copie o arquivo JAR da sua aplicação para o container
COPY target/*.jar app.jar

# Exponha a porta que sua aplicação usa (geralmente 8080)
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]