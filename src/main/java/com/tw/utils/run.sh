#!/bin/sh
ORDER="$1"
if [ "$ORDER" = "1" ]; then
 java CSVReader
elif [ "$ORDER" = "2" ]; then
  java CSVReader
else
  echo "Unknown company: $ORDER";
  exit 1;
fi

