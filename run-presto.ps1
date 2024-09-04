# Check if the container already exists and remove it if necessary
if (docker ps -q -f name=presto) {
    Write-Output "Stopping and removing existing Presto container..."
    docker stop presto
    docker rm presto
}

# Run the Docker Container with the specified configurations
docker run -d --name presto `
  -v ${PWD}/config.properties:/etc/presto/config.properties `
  -v ${PWD}/jvm.config:/etc/presto/jvm.config `
  -p 8080:8080 prestodb/presto:latest

Write-Output "Presto container started successfully on port 8080"
