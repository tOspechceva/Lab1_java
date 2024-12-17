# Первый этап: сборка с Maven + JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
# Заранее загружаем зависимости, чтобы улучшить кэширование
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Второй этап: запуск собранного приложения на легковесном образе JDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]