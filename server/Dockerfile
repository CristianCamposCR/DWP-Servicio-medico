# Utiliza una imagen base con OpenJDK 21 y Maven
FROM maven:3.9.5-amazoncorretto-17 as builder
# Establece el directorio de trabajo
WORKDIR /app
# Copia el archivo POM
COPY pom.xml .
# Copia el código fuente
COPY src ./src
# Copiar el archivo de propiedades
COPY /propertiestodocker/application.properties /app/src/main/resources/application.properties
COPY /propertiestodocker/secret.properties /app/src/main/resources/secret.properties

# Empaqueta la aplicación
RUN mvn clean package -DskipTests
# Utiliza una imagen de Java 17
FROM openjdk:17.0.2-jdk
# Copia el archivo JAR construido
COPY --from=builder /app/target/server-0.0.1-SNAPSHOT.jar /app.jar
# Exponer el puerto 8080
EXPOSE 8080
# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "app.jar"]



