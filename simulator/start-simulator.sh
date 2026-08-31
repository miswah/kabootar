#!/bin/bash

set -e

mvn clean install

IMAGE_NAME="kabootar-multi-region-simulator"

# Build image
sudo docker build -t "$IMAGE_NAME" .

# Mumbai
sudo docker rm -f kabootar-mumbai-01 2>/dev/null || true

sudo docker run -d \
  --name kabootar-mumbai-01 \
  -p 8091:8080 \
  -e APP_SIMULATOR_REGION_NAME="ap-south-mumbai" \
  -e APP_SIMULATOR_INSTANCE_NAME="demo-mumbai-01" \
  -e APP_SIMULATOR_SERVICE_NAME="simulator-1" \
  -e SERVER_PORT=8080 \
  "$IMAGE_NAME"


# Singapore
sudo docker rm -f kabootar-singapore-01 2>/dev/null || true

sudo docker run -d \
  --name kabootar-singapore-01 \
  -p 8092:8080 \
  -e APP_SIMULATOR_REGION_NAME="ap-southeast-singapore" \
  -e APP_SIMULATOR_INSTANCE_NAME="demo-singapore-01" \
  -e APP_SIMULATOR_SERVICE_NAME="simulator-2" \
  -e SERVER_PORT=8080 \
  "$IMAGE_NAME"


# Frankfurt
sudo docker rm -f kabootar-frankfurt-01 2>/dev/null || true

sudo docker run -d \
  --name kabootar-frankfurt-01 \
  -p 8093:8080 \
  -e APP_SIMULATOR_REGION_NAME="eu-central-frankfurt" \
  -e APP_SIMULATOR_INSTANCE_NAME="demo-frankfurt-01" \
  -e APP_SIMULATOR_SERVICE_NAME="simulator-3" \
  -e SERVER_PORT=8080 \
  "$IMAGE_NAME"


echo ""
echo "================================="
echo "Kabootar simulator instances"
echo "================================="

sudo docker ps --filter "name=kabootar-"
