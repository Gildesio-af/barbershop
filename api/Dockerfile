FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Layer Caching
COPY pom.xml .

# Baixa as dependências
RUN mvn dependency:go-offline

COPY src ./src

# Compila o projeto.
RUN mvn clean package -DskipTests

# Runtime
# Imagem JRE
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Criar um usuário restrito para não rodar como Root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copia apenas o JAR gerado no estágio anterior
# Renomeamos para app.jar para facilitar o ENTRYPOINT
COPY --from=builder /app/target/*.jar app.jar

# Dá permissão ao usuário restrito
RUN chown appuser:appgroup app.jar

# Troca para o usuário seguro
USER appuser

# Expõe a porta
EXPOSE 8080

# Configurações da JVM para Containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]