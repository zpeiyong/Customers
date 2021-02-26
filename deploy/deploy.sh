#!/usr/bin/env bash

name=$1
service=$2
version=$3
if [ ! -n "$name" ];
then
    echo "Need branch name"
    exit 1
fi
if [ ! -n "$service" ];
then
    echo "Need service name"
    exit 1
fi
if [ ! -n "$version" ];
then
    echo "Need service version"
    exit 1
fi

# Environment
export JAVA_HOME=/usr/local/java/jdk1.8.0_281
export PATH=$JAVA_HOME/bin:$PATH
export BASE_HOME=/opt/coding/wxhg-infectDisMonitor-back

# Spring Env
SPRING_ENV="test"
if [[ "$name" == "branch-master" ]];
then
    SPRING_ENV="prod"
fi

# Deploy
cd $BASE_HOME

# kill process if exist
PID=`cat ./"$service"/process.pid`
if ps -p $PID > /dev/null;
then
  kill $PID
  sleep 3s  # sleep 3 secs
  echo "Service $service has been killed! PID: $PID"
else
  echo "Service $service is not running..."
fi

nohup java -jar ./${service}/${service}-${version}.jar --spring.profiles.active=${SPRING_ENV} > ${service}.log 2>&1 &
echo $! > ./${service}/process.pid

