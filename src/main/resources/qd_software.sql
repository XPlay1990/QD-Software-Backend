INSERT IGNORE INTO organization(name, identifier_key, fall_back_email, is_active, creation_time,
                                is_customer)
VALUES ('QD Software', 0, 'j_adamczyk@hotmail.com', TRUE, NOW(), false);

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('QD_Software', 'j_adamczyk@hotmail.com', '$2a$10$h0sVyqewi6BvMrhuII87qONG/55DKhR6UQ0UWc1YXfiQWgRaaRwem', 'QD',
        'Software', (SELECT id from organization where name = 'QD Software'), TRUE, NOW());

INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'QD_Software'),
        (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'QD_Software'), (SELECT id from role where role_name = 'ROLE_ADMIN'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('SampleUser', 'j_adamczyk@hotmail.com', '$2a$10$h0sVyqewi6BvMrhuII87qONG/55DKhR6UQ0UWc1YXfiQWgRaaRwem',
        'QD',
        'Software', (SELECT id from organization where name = 'QD Software'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'SampleUser'),
        (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));

INSERT IGNORE INTO user(username, email, password, first_name, last_name, organization_id, is_active, creation_time)
VALUES ('jan', 'j_adamczyk@hotmail.com', '$2a$10$h0sVyqewi6BvMrhuII87qONG/55DKhR6UQ0UWc1YXfiQWgRaaRwem', 'Jan',
        'Adamczyk', (SELECT id from organization where name = 'QD Software'), TRUE, NOW());
INSERT IGNORE INTO user_roles(user_id, roles_id)
VALUES ((SELECT id from user where username = 'jad'), (SELECT id from role where role_name = 'ROLE_REGISTERED_USER'));