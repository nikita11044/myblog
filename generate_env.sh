#!/bin/bash

rm -f .env

awk -F= 'NF > 1 {print $1"="$2}' src/main/resources/application.properties > .env

echo "✅ .env file has been generated from application.properties"
