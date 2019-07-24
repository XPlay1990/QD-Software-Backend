INSERT IGNORE INTO role(role_name)
VALUES ('ROLE_ADMIN');

INSERT IGNORE INTO role(role_name)
VALUES ('ROLE_REGISTERED_USER');

INSERT IGNORE INTO role(role_name)
VALUES ('ROLE_CUSTOMER');

INSERT IGNORE INTO role(role_name)
VALUES ('ROLE_SUPPLIER');

INSERT IGNORE INTO organization(name, identifier_key, fall_back_email, is_active, creation_time)
VALUES ('Nicando', 0, 'edi-support@nicando.de', TRUE, NOW());

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('Nicando', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Nicando',
        'Software', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'Nicando'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'Nicando'), (SELECT id from role where role_name = 'ROLE_ADMIN'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('EdiSampleUser', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Nicando',
        'Software', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'Nicando'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));


INSERT IGNORE INTO question(question_de, question_en)
VALUES ('Sind Sie bereits an unsere Abbino Plattform angebunden?', 'Are you already connected to our Abbino Platform?');

INSERT IGNORE INTO question(question_de, question_en)
VALUES ('Welche Austauschformate unterstützen Sie?
Bitte geben Sie die unterstützte Version des Standards mit an.
Beispiele: SAP IDOC, EDIFACT, VDA, BEMIS',
        'Which formats do you support?
Please also write which Version you use.
Examples are: SAP IDOC, EDIFACT, VDA, BEMIS');

