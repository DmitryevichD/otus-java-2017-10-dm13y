psql --command="CREATE DATABASE hw_09"
psql --command="CREATE USER hw_09 WITH PASSWORD 'hw_09' LOGIN"
mvn liquibase:update