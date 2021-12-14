#!/bin/sh
JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk
PATH=${JAVA_HOME}/bin:$PATH
CLASSPATH=.:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export JAVA_HOME
export PATH
export CLASSPATH

java -version

ORDER="$1"
COMPANY_ONE="$2"
COMPANY_TWO="$3"
if [ "$ORDER" = "1" ]; then
  java fortest $COMPANY_ONE $COMPANY_TWO
elif [ "$ORDER" = "2" ]; then
  java fortest $COMPANY_TWO $COMPANY_ONE
else
  echo "Unknown company: $ORDER"
  exit 1
fi
