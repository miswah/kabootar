FROM eclipse-temurin:25.0.4_7-jre-alpine-3.24
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
EXPOSE 8091
EXPOSE 8092
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "app.jar"]