#!/bin/bash

# Check if the container already exists and remove it if necessary
if [ "$(docker ps -aq -f name=presto)" ]; then
    echo "Stopping and removing existing Presto container..."
    docker stop presto
    docker rm presto
fi

# Run the Docker Container with the specified configurations
docker run -d --name presto \
  -v $(pwd)/config.properties:/etc/presto/config.properties \
  -v $(pwd)/jvm.config:/etc/presto/jvm.config \
  -p 8080:8080 prestodb/presto:latest

echo "Presto container started successfully on port 8080"
