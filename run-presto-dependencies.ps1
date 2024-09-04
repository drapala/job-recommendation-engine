if (-Not (Test-Path "config.properties")) {
    @"
coordinator=true
node-scheduler.include-coordinator=true
http-server.http.port=8080
discovery-server.enabled=true
discovery.uri=http://localhost:8080
"@ | Out-File -FilePath "config.properties" -Encoding UTF8
    Write-Output "config.properties file created."
}

if (-Not (Test-Path "jvm.config")) {
    @"
-server
-Xmx2G
-XX:+UseG1GC
-XX:G1HeapRegionSize=32M
-XX:+UseGCOverheadLimit
-XX:+ExplicitGCInvokesConcurrent
-XX:+HeapDumpOnOutOfMemoryError
-XX:+ExitOnOutOfMemoryError
-Djdk.attach.allowAttachSelf=true
"@ | Out-File -FilePath "jvm.config" -Encoding UTF8
    Write-Output "jvm.config file created."
}

docker run -d --name presto `
  -v ${PWD}/config.properties:/etc/presto/config.properties `
  -v ${PWD}/jvm.config:/etc/presto/jvm.config `
  -p 8080:8080 prestodb/presto:latest

Write-Output "Presto Docker is executing"
