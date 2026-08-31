#!/bin/bash

set -e

CONTAINERS=(
  "kabootar-mumbai-01"
  "kabootar-singapore-01"
  "kabootar-frankfurt-01"
)

echo "Stopping containers..."

for container in "${CONTAINERS[@]}"; do
  if sudo docker ps -q --filter "name=^${container}$" | grep -q .; then
    echo "Stopping $container"
    sudo docker stop "$container"
  else
    echo "$container is not running"
  fi
done

echo ""
echo "Removing containers..."

for container in "${CONTAINERS[@]}"; do
  if sudo docker ps -aq --filter "name=^${container}$" | grep -q .; then
    echo "Removing $container"
    sudo docker rm "$container"
  else
    echo "$container does not exist"
  fi
done

echo ""
echo "All Kabootar simulator containers stopped and removed."
