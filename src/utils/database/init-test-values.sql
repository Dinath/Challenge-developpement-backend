
USE challenge;
SET NAMES 'utf8' COLLATE 'utf8_general_ci';

---
--- User groups
---
INSERT INTO entityusergroup VALUES (1, "Administrateur");
INSERT INTO entityusergroup VALUES (2, "Visiteur");
INSERT INTO entityusergroup VALUES (3, "Banni");

---
--- Users
---
--- Password is a bcrypt hashed password using cost of 10 and plain password "alex"
---
INSERT INTO entityuser (entityemail, entitygroupid, entitypassword) VALUES ('a.soyer@outlook.fr', 1, '$2a$10$H7HJ/l9MNM7fP1YE59d3A.wLJcv1qNJxPSeO6Ug2HvxRWGdkOKaEK');

--- 
--- Questions
---
INSERT INTO entityquestion VALUES (1, 'Qui sera embauché chez Nethoes ?');
INSERT INTO entityquestion VALUES (2, 'Pourquoi Nicolas Hulot a quitté le gouvernement ?');
INSERT INTO entityquestion VALUES (3, 'Comment devenir génial ?');

---
--- Anwers
---
INSERT INTO entityanswer (entitylabel, entityquestion) VALUES ('Maître Gims, sans suprise.', 1);
INSERT INTO entityanswer (entitylabel, entityquestion) VALUES ('On se le demande...', 2);
INSERT INTO entityanswer (entitylabel, entityquestion) VALUES ('Si seulement quelqu''un le savait !', 3);

---
--- Tagss
---
INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Recrutement', 1);
INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Musique', 1);

INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Ecologie', 2);
INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Gouvernement', 2);
INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Politique', 2);

INSERT INTO entityquestiontag (entitylabel, entityquestion) VALUES ('Développement personnel', 3);