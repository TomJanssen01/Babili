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
