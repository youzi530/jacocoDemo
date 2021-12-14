#!/bin/sh
JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk
PATH=${JAVA_HOME}/bin:$PATH
CLASSPATH=.:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export JAVA_HOME
export PATH
export CLASSPATH

echo "CLASSPATH"

ORDER="$1"
if [ "$ORDER" = "1" ]; then
 java CSVReader
elif [ "$ORDER" = "2" ]; then
  java CSVReader
else
  echo "Unknown company: $ORDER";
  exit 1;
fi

