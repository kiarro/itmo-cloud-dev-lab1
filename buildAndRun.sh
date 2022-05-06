#!/bin/sh
mvn clean package && docker build -t com.lab1/dragon .
docker rm -f dragon || true && docker run -d -p 8080:8080 -p 4848:4848 --name dragon com.lab1/dragon 
