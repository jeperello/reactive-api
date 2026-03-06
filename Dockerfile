# Etapa 1: Compilación (Build)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Usamos el wrapper para que sea independiente del sistema
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos solo el JAR resultante de la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de WebFlux
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
