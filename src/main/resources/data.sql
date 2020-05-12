-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'admin', '$2a$10$fNEP0vLPDboAhB7ZM1vUxOzsj2Wt5IsY1hH3FYLUaE4YyQYFAZVOC', 'Admin', 'admin@admin.admin', 'ADMIN');

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'talba', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Talha Bayansar', 'talha@talha.talha', 'STUDENT');

INSERT INTO USER
(ID, USERNAME, PASSWORD, NAME, EMAIL, ROLE)
VALUES
(nextval('USER_SEQ'), 'talha_docent', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'Talha Bayansar', 'talha@talha.talha', 'TEACHER');

insert into Cursus
(ID, NAAM, BESCHRIJVING, DOCENT_ID)
values
(nextval ('cursus_seq'), 'testcursus1', 'Om te testen', 2);

insert into opdracht
(ID, TITEL, OPGAVE, VOORBEELD, BEOORDELING, CURSUS_Id)
values
(nextval ('opdracht_seq'), 'opdracht week 1: Ga naar de bakker', 'Ga naar de bakker in jouw buurt en koop een brood en iets lekkers voor jezelf of jouw gezin', 'C:/Test/Audio/Examples/opdracht1.wav', 'moeilijk', 0);

insert into inlevering
(ID, AUDIO_PATH, OPDRACHT_Id, user_Id)
values
(nextval ('inlevering_seq'), '/audio/bakker.wav', 0, 1);


