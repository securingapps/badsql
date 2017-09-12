CREATE DATABASE BADSQL;
USE BADSQL;

CREATE TABLE USERS (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL,
email VARCHAR(50)
);

INSERT INTO USERS(login,password,email) VALUES ("user1","MD5(pwd1)","user1@badsql.ch");
INSERT INTO USERS(login,password,email) VALUES ("user2","MD5(pwd2)","user2@badsql.ch");
INSERT INTO USERS(login,password,email) VALUES ("user3","MD5(pwd3)","user3@badsql.ch");

CREATE TABLE SPORTS (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
userid INT(6) NOT NULL,
sport VARCHAR(30) NOT NULL
);

INSERT INTO SPORTS(userid,sport) VALUES (1,"football");
INSERT INTO SPORTS(userid,sport) VALUES (1,"tennis");
INSERT INTO SPORTS(userid,sport) VALUES (1,"ski");
INSERT INTO SPORTS(userid,sport) VALUES (2,"boxe");
INSERT INTO SPORTS(userid,sport) VALUES (2,"curling");
INSERT INTO SPORTS(userid,sport) VALUES (3,"football");

select SPORTS.sport FROM SPORTS INNER JOIN USERS ON SPORTS.userid=USERS.id WHERE USERS.login="user1";
