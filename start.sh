#!/bin/sh
cd /Users/zhangxiao/eclipse-workspace/file-preview-server
time=$(date "+%Y%m%d-%H%M%S")
#JAVA_HOME=/root/TKB-Test/jdk1.8.0_211
nohup  java -jar -Dserver.port=8080  target/file-view.jar   > log-${time}.log 2>&1 &
