USE challenge;
SET NAMES 'utf8' COLLATE 'utf8_general_ci';

CREATE TABLE IF NOT EXISTS entityusergroup (
	id TINYINT NOT NULL AUTO_INCREMENT,
	entityname varchar(30) NOT NULL,
	UNIQUE(entityname),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS entityuser (
	id int NOT NULL AUTO_INCREMENT,
	entityemail varchar(255) NOT NULL,
	entitypassword TEXT NOT NULL,
	entitygroupid TINYINT NOT NULL,
	UNIQUE(entityemail),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS entityquestion (
	id int NOT NULL AUTO_INCREMENT,
	entitylabel TEXT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS entityanswer (
	id int NOT NULL AUTO_INCREMENT,
	entitylabel TEXT NOT NULL,
	entityquestion int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (entityquestion)
        REFERENCES entityquestion (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS entityquestiontag (
	id int NOT NULL AUTO_INCREMENT,
	entitylabel varchar(30) NOT NULL,
	entityquestion int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (entityquestion)
        REFERENCES entityquestion (id)
        ON DELETE CASCADE
);

