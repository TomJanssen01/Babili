ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 0 minvalue 0;
insert into BABILIUSER
(ID, USERNAME, PASSWORD, NAAM, EMAILADDRESS)
values
(nextval ('HIBERNATE_SEQUENCE'), 'tojan', 'QsDf01', 'Tom Janssen', 'tom.janssen@student.thomasmore.be');

insert into BABILIUSER
(ID, USERNAME, PASSWORD, NAAM, EMAILADDRESS)
values
(nextval ('HIBERNATE_SEQUENCE'), 'stdel', 'AeZrPo78514', 'Stefaan Delplanque', 'stefaan.delplanque@student.thomasmore.be');

insert into BABILIUSER
(ID, USERNAME, PASSWORD, NAAM, EMAILADDRESS)
values
(nextval ('HIBERNATE_SEQUENCE'), 'tabay', 'ApKNklINyt87965', 'Talha Bayansar', 'talha.bayansar@student.thomasmore.be');

insert into BABILIUSER
(ID, USERNAME, PASSWORD, NAAM, EMAILADDRESS)
values
(nextval ('HIBERNATE_SEQUENCE'), 'lihae', 'OJuYtoNNv7842@u2', 'Lies Van der Haegen', 'lies.van.der.haegen@student.thomasmore.be');

insert into cursus
(ID, DOCENTID, CURSISTID, NAAM, DESCRIPTION)
values
(nextval ('cursus_seq'), 0, 1, 'Nederlands voor beginners', 'dit is een cursus nederlands voor beginners');

insert into cursus
(ID, DOCENTID, CURSISTID,NAAM, DESCRIPTION)
values
(nextval ('cursus_seq'), 1, 1, 'Nederlands voor beginners', 'dit is een cursus nederlands voor beginners');

insert into cursus
(ID, DOCENTID, CURSISTID,NAAM, DESCRIPTION)
values
(nextval ('cursus_seq'), 2, 0, 'Nederlands voor intermediairs', 'dit is een cursus nederlands voor gemiddeld niveau');

insert into cursus
(ID, DOCENTID, CURSISTID, NAAM, DESCRIPTION)
values
(nextval ('cursus_seq'),0, 3,  'Nederlands voor gevorderden', 'dit is een cursus nederlands voor gevorderden');

insert into opdracht
(ID, CURSUSID, TITLE, TASK, EXAMPLE, REVIEW)
values
(nextval ('opdracht_seq'), 0, 'Oefening week 1', 'Ga naar de bakker en bestel een brood en 3 koffiekoeken', 'Hallo, mag ik een brood en 3 koffiekoeken alstublieft', 'moeilijk');

insert into inlevering
(CURSISTID, OPDRACHTID, AUDIOPATH, FEEDBACK)
values
(0, 1, '/audio/audio1.wav', 'goed gedaan, doe zo voort');

insert into inlevering
(CURSISTID, OPDRACHTID, AUDIOPATH, FEEDBACK)
values
(1, 0, '/audio/audio2.wav', 'mooie uitspraak, opdracht perfect uitgevoerd');