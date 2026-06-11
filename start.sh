#!/bin/sh
# start.sh: 모든 모듈을 동시에 실행하는 스크립트

echo "Starting API module..."
java -jar /app/api.jar &

echo "Starting CORP module..."
java -jar /app/corp.jar &

echo "Starting BATCH module..."
java -jar /app/batch.jar &

# 모든 백그라운드 프로세스가 종료될 때까지 대기
wait
