#!/usr/bin/env bash
set -e

# Change directory to script location
cd "$(dirname "$0")"

echo "================================================================="
echo "                    Starting Kabootar                            "
echo "================================================================="


for service in admin alert common configuration gateway monitor simulator; do
    echo "-----------------------------------------------------------------"
    echo " Building $service..."
    echo "-----------------------------------------------------------------"
    (cd "$service" && mvn clean package -DskipTests)
done


echo "================================================================="
echo "           Starting Docker Compose Containers                    "
echo "================================================================="

# Shut down any previous runs
sudo docker compose down --remove-orphans

# Build containers and run in background
sudo docker compose up --build -d




echo "================================================================="
echo "           Containers Successfully Launched!                     "
echo "================================================================="
echo "You can check logs with:   docker compose logs -f"
echo "Check running containers:  docker compose ps"
echo "================================================================="