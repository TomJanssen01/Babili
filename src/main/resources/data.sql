-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO USER
(ID, USERNAME, PASSWORD, ROLE)
VALUES
(nextval('USER_SEQ'), 'admin', '$2a$10$fNEP0vLPDboAhB7ZM1vUxOzsj2Wt5IsY1hH3FYLUaE4YyQYFAZVOC', 'ADMIN');
INSERT INTO USER
(ID, USERNAME, PASSWORD, ROLE)
VALUES
(nextval('USER_SEQ'), 'talha@talha.talha', '$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy', 'TEACHER');