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

insert into Cursus
(ID, NAAM, BESCHRIJVING, docent_Id)
values
(nextval ('cursus_seq'), 'testcursus1', 'Om te testen', 2);

insert into opdracht
(ID, TITEL, OPGAVE, VOORBEELD, BEOORDELING, CURSUS_Id)
values
(nextval ('opdracht_seq'), 'bakker', 'ga naar een test', 'bestel een brood', 'moeilijk', 0);

insert into inlevering
(ID, AUDIO_PATH, FEEDBACK, OPDRACHT_Id)
values
(nextval ('inlevering_seq'), '/audio/bakker.wav', 'dit is feedback', 0);


