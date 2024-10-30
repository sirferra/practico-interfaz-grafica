# Usar la imagen oficial de Maven para compilar el proyecto
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias de Maven
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copiar el código fuente del proyecto
COPY src ./src

# Compilar el proyecto con Maven
RUN mvn clean package

# Usar una imagen más ligera para la ejecución
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo para la aplicación
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=build /app/target/venta-bici-1.0-SNAPSHOT.jar app.jar

# Especificar el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
