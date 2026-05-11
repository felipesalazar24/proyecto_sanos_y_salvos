# ETAPA DE CONSTRUCCIÓN
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copiar wrappers y configuración
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Corregir finales de línea de Windows (CRLF a LF) y dar permisos
RUN tr -d '\r' < gradlew > gradlew.tmp && mv gradlew.tmp gradlew
RUN chmod +x gradlew

# Copiar el código fuente
COPY src src

# COMPILACIÓN:
# 1. Desactivamos la descarga automática de Toolchains.
# 2. Obligamos a Gradle a usar el JAVA_HOME de esta imagen (que es Java 25).
RUN ./gradlew clean build -x test \
    -Porg.gradle.java.installations.auto-download=false \
    -Dorg.gradle.java.home=$JAVA_HOME

# ETAPA DE EJECUCIÓN
FROM eclipse-temurin:25-jre
WORKDIR /app

# Como tienes 'jar { enabled = false }', solo existirá un .jar (el ejecutable)
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

# 2. Forzamos a Spring a usar el 8081 mediante una variable de entorno
ENV SERVER_PORT=8081

ENTRYPOINT ["java", "-jar", "app.jar"]