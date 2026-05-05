# Imagem base com JDK 21 usada para compilar o projeto ↓↓↓↓
FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY pom.xml pom.xml
COPY src/ src/

RUN chmod +x mvnw && ./mvnw -q -DskipTests package

# Vai rodar o .jar sem precisar compilar ↓↓↓↓
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
