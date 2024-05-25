DROP TABLE IF EXISTS USER_INFO;
CREATE TABLE USER_INFO (
 id varchar(512) not null, 
 firstName varchar(256) not null, 
 lastName varchar(256) not null, 
 userName varchar(256) not null, 
 email varchar(2000), 
 password varchar(2000), 
 roles varchar(2000),
 PRIMARY KEY(id)
);