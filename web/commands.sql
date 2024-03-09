CREATE DATABASE SuperMario;

USE SuperMario;

CREATE TABLE leaderboard (
   username varchar(16) NOT NULL,
   points int NOT NULL,
   timestamp datetime PRIMARY KEY
);
