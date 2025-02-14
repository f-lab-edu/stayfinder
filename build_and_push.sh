#!/bin/bash

IMAGE_NAME="stayfinder"
TAG="latest"
# shellcheck disable=SC2034
CONTAINER_NAME="stayfinder"

echo "📌 기존 컨테이너 중지 및 삭제..."
docker-compose down

echo "🧹 불필요한 Docker 이미지 및 컨테이너 정리..."
docker system prune -af

echo "🚀 새로운 이미지 빌드 중 (캐시 사용 안 함)..."
docker build --no-cache -t $IMAGE_NAME:$TAG .

echo "📤 Docker Hub로 이미지 Push..."
docker push $IMAGE_NAME:$TAG

echo "🔄 최신 이미지 Pull..."
docker pull $IMAGE_NAME:$TAG

echo "🚢 Docker Compose로 컨테이너 실행..."
docker-compose up -d --build

echo "📜 실행 중인 컨테이너 확인..."
docker ps -a
