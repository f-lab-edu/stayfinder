# 1️⃣ Gradle 빌드 컨테이너 (캐시 없이 매번 새롭게 빌드)
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

# ✅ 프로젝트 전체 소스 코드 복사
COPY . .

# ✅ Gradle 빌드 실행 (모든 모듈의 bootJar 생성)
RUN chmod +x ./gradlew
RUN ./gradlew clean :api:bootJar :batch:bootJar :corp:bootJar -x test --no-daemon

# ✅ 빌드 후 Gradle 캐시 삭제
RUN rm -rf /root/.gradle

# 2️⃣ 최종 실행용 컨테이너
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# ✅ 각 모듈의 JAR 파일 복사
COPY --from=builder /app/api/build/libs/api*.jar /app/api.jar
COPY --from=builder /app/corp/build/libs/corp*.jar /app/corp.jar
COPY --from=builder /app/batch/build/libs/batch*.jar /app/batch.jar

# ✅ start.sh 스크립트 복사 및 실행 권한 부여
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# ✅ 필요한 포트 노출 (각 모듈이 다른 포트를 사용할 경우 조정)
EXPOSE 8080 8081 8082

# ✅ 모든 모듈을 동시에 실행할 start.sh 스크립트로 ENTRYPOINT 설정
ENTRYPOINT ["/app/start.sh"]
