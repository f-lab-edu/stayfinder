# 1️⃣ Gradle 빌드 컨테이너 (캐시 없이 매번 새롭게 빌드)
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

# ✅ Gradle 빌드 캐시 없이 실행
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test bootJar --no-daemon

RUN rm -rf /root/.gradle

# 2️⃣ 최종 실행용 컨테이너
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# ✅ 빌드된 JAR 파일만 복사
COPY --from=builder /app/build/libs/*.jar /app/stayfinder.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/stayfinder.jar"]