CREATE DATABASE IF NOT EXISTS db_example;

CREATE TABLE IF NOT EXISTS db_example.users (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255), age INT(3) NOT NULL DEFAULT 0, PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS db_example.login (id BIGINT(20) NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL, passwordMD5 VARCHAR(255), PRIMARY KEY (id));

INSERT INTO login (id, username, passwordMD5) VALUES (1, "admin", "a16d4c15a479c7826904758ab6e477ce");