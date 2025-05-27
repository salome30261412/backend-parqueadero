# Etapa de construcción
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Agregar un paso de depuración para ver los archivos generados
RUN ls -l /app/target

# Etapa de ejecución
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el archivo JAR
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

