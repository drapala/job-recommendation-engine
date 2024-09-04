#!/bin/bash

if [ ! -f config.properties ]; then
  echo "coordinator=true" > config.properties
  echo "node-scheduler.include-coordinator=true" >> config.properties
  echo "http-server.http.port=8080" >> config.properties
  echo "discovery-server.enabled=true" >> config.properties
  echo "discovery.uri=http://localhost:8080" >> config.properties
  echo "config.properties file created."
fi

if [ ! -f jvm.config ]; then
  echo "-server" > jvm.config
  echo "-Xmx2G" >> jvm.config
  echo "-XX:+UseG1GC" >> jvm.config
  echo "-XX:G1HeapRegionSize=32M" >> jvm.config
  echo "-XX:+UseGCOverheadLimit" >> jvm.config
  echo "-XX:+ExplicitGCInvokesConcurrent" >> jvm.config
  echo "-XX:+HeapDumpOnOutOfMemoryError" >> jvm.config
  echo "-XX:+ExitOnOutOfMemoryError" >> jvm.config
  echo "-Djdk.attach.allowAttachSelf=true" >> jvm.config
  echo "jvm.config file created."
fi

docker run -d --name presto \
  -v $(pwd)/config.properties:/etc/presto/config.properties \
  -v $(pwd)/jvm.config:/etc/presto/jvm.config \
  -p 8080:8080 prestodb/presto:latest

echo "Presto Docker is executing."
