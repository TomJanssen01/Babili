-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'talha_docent', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Talha Bayansar', 'talha@talha.talha', 'TEACHER');

insert into Cursus
(ID, NAAM, BESCHRIJVING, DOCENT_ID)
values
(nextval ('cursus_seq'), 'testcursus1', 'Om te testen', 0);

insert into Cursus
(ID, NAAM, BESCHRIJVING, DOCENT_ID)
values
(nextval ('cursus_seq'), 'testcursus2', 'Om te testen', 0);

insert into Cursus
(ID, NAAM, BESCHRIJVING, DOCENT_ID)
values
(nextval ('cursus_seq'), 'testcursus3', 'Om te testen', 0);

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'admin', '$2a$10$fNEP0vLPDboAhB7ZM1vUxOzsj2Wt5IsY1hH3FYLUaE4YyQYFAZVOC', 'Admin', 'admin@admin.admin', 'ADMIN');

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE, CURSUS_ID)
VALUES
(nextval('USER_SEQ'), 'talba', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Talha Bayansar', 'talha@talha.be', 'STUDENT', 1);

insert into opdracht
(ID, TITEL, OPGAVE, VOORBEELD, BEOORDELING, CURSUS_Id)
values
(nextval ('opdracht_seq'), 'opdracht week 1: Ga naar de bakker', 'Ga naar de bakker in jouw buurt en koop een brood en iets lekkers voor jezelf of jouw gezin', 'C:/Test/Audio/Examples/opdracht1.wav', 'moeilijk', 1);

insert into inlevering
(ID, AUDIO_PATH, OPDRACHT_Id, user_Id)
values
(nextval ('inlevering_seq'), '/audio/bakker.wav', 0, 1);


