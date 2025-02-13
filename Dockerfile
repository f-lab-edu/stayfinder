FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test bootJar

RUN rm -rf /root/.gradle

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/stayfinder.jar stayfinder.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "stayfinder.jar"]
