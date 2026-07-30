# Stage 1: Build de la aplicación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilamos y generamos el WAR (ignorando tests)
RUN mvn clean package -DskipTests

# Stage 2: Runtime con Open Liberty
FROM openliberty/open-liberty:latest

# Copiamos la configuración de Liberty
COPY --chown=1001:0 src/main/liberty/config/server.xml /config/

# Copiamos el WAR generado (se renombra a refugio-animal.war dentro del contenedor)
COPY --chown=1001:0 --from=build /app/target/*.war /config/apps/refugio-animal.war

# Copiamos el driver de PostgreSQL al directorio real de shared.resource.dir
# de la imagen oficial de Open Liberty (server.xml lo referencia como
# ${shared.resource.dir}, la misma variable que usa "mvn liberty:dev" en local)
COPY --chown=1001:0 --from=build /app/target/dependency/postgresql-*.jar /opt/ol/wlp/usr/shared/resources/

# Instalamos características necesarias
RUN features.sh

EXPOSE 9080 9443