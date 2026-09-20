FROM eclipse-temurin:25.0.4_7-jre-alpine-3.24
RUN apk update && apk upgrade
WORKDIR /app

# 1. Create a non-privileged system group and user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 2. Copy the jar and explicitly give ownership to the new user
COPY --chown=appuser:appgroup target/*.jar app.jar

# 3. Switch away from root to the non-root user
USER appuser

EXPOSE 8082
EXPOSE 8091
EXPOSE 8092
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "app.jar"]