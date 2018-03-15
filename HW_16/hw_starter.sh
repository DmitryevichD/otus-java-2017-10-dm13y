#!/usr/bin/env bash
mvn clean install;
java -Xms64M -Xmx64M -jar ././msg-system/target/msg-core.jar&
sleep 1;
java -Xms64M -Xmx64M -jar ././msg-db-service/target/msg-db-service.jar&
java -Xms64M -Xmx64M -jar ././msg-db-service/target/msg-db-service.jar&
java -Xms64M -Xmx64M -jar ././msg-db-service/target/msg-db-service.jar&
sleep 5;
echo "+==========================================================+"
echo "|       PLEASE DEPLOY msg-web-service.war ON 8088 PORT     |"
echo "+==========================================================+"
wait

