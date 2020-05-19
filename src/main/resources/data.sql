-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'marc_docent', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Marc Decaluwe', 'marc.decaluwe@thomasmore.be', 'TEACHER'),
(nextval('USER_SEQ'), 'vera_docent', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Vera Peeters', 'vera.peeters@thomasmore.be', 'TEACHER')
;

insert into Cursus
(ID, NAAM, BESCHRIJVING, DOCENT_ID)
values
(nextval ('cursus_seq'), 'Nederlands voor beginners', 'In deze cursus leer je de basis van het Nederlands, niveau A1.', 0),
(nextval ('cursus_seq'), 'Nederlands voor gevorderden', 'In deze cursus leer je meer gevorderde technieken van het Nederlands, niveau A2.', 0),
(nextval ('cursus_seq'), 'Nederlands voor geavanceerden', 'In deze cursus leer je de geavanceerde en meer ingewikkelde gesprekken voeren in het Nederlands, niveau B1.', 1)
;

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE, CURSUS_ID)
VALUES
(nextval('USER_SEQ'), 'marc_student', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Marc Decaluwe', 'marc.decaluwe@student.thomasmore.be', 'STUDENT', 0),
(nextval('USER_SEQ'), 'vera_student', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Vera Peeters', 'vera.peeters@student.thomasmore.be', 'STUDENT',2),
(nextval('USER_SEQ'), 'Tom', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Tom De Boeck', 'Tom@Tom.be', 'STUDENT', null),
(nextval('USER_SEQ'), 'Sara', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Sara van der Heyden', 'saravdh@hotmail.be', 'STUDENT', null)
;

insert into opdracht
(ID, TITEL, OPGAVE, VOORBEELD,  CURSUS_ID)
values
(nextval ('opdracht_seq'), 'Bakker', 'Ga naar de bakker in jouw buurt en koop een brood en iets lekkers voor jezelf of jouw gezin', '/audioFiles/bakkerexample/opdracht1.wav', 0),
(nextval ('opdracht_seq'), 'Supermarkt', 'Ga naar een supermarkt in je beurt en vraag meer uitleg over een product dat je wil kopen.', '/audioFiles/bakkerexample/opdracht1.wav', 1),
(nextval ('opdracht_seq'), 'Corona', 'Spreek met iemand uit je omgeving over het coronavirus.', '/audioFiles/bakkerexample/opdracht1.wav', 2)
;



