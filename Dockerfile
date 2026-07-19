# ---- Etapa 1: compilar el .war con Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Etapa 2: desplegar en WildFly 26 ----
FROM quay.io/wildfly/wildfly:26.1.3.Final-jdk17
COPY --from=build /app/target/refugio-animal.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]