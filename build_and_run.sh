#!/bin/sh

echo "Building Open Trace Instance"
docker-compose up --no-start open-trace

echo "Build PostgreSQL Instance"
docker-compose up --no-start db

echo "Starting PostgreSQL Database"
docker-compose start db

echo "Starting Open Trace Docker Container"
docker-compose start open-trace

echo "Building application"
mvn clean package -Pnative

echo: "Starting application"
./target/quarkus-example-1.0.0-SNAPSHOT-runner
