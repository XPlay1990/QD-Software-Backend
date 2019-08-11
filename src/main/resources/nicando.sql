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
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('slübbers', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Stefan',
        'Lübbers', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('mradke', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Mario',
        'Radke', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('oar', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Onur',
        'Arslan', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('nvraux', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Nicolas',
        'Vraux', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('afrö', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Andreas',
        'Fröhlich', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('jad', 'edi-support@nicando.de', '$2a$10$LiREBwIdY3bXDy9M0OdzmOFj89Xh4Ar.XohcrBUPdNDK7V8p1JazO', 'Jan',
        'Adamczyk', (SELECT id from organization where name = 'Nicando'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'EdiSampleUser'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));