# Etapa de construcción
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/mi-aplicacion.jar app.jar  # Especifica el nombre exacto del archivo JAR generado

# Exponer el puerto que utiliza la aplicación (si es 8080, está bien)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
