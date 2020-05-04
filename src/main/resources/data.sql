insert into Cursus
(ID, NAAM, BESCHRIJVING, docent_Id)
values
(0, 'testcursus1', 'Om te testen', 2);

insert into opdracht
(ID, TITEL, OPGAVE, VOORBEELD, BEOORDELING, CURSUS_Id)
values
(0, 'bakker', 'ga naar een test', 'bestel een brood', 'moeilijk', 0);

insert into inlevering
(ID, AUDIOPATH, FEEDBACK, OPDRACHT_Id)
values
(0, '/audio/bakker.wav', 'dit is feedback', 0);
