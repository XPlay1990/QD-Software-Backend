# ERCO
INSERT IGNORE INTO address(city, country, house_number, postcode, street)
VALUES ('Lüdenscheid', 'Germany', '80-82', '58507', 'Brockhauser Weg');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'ERCO', NOW(),
        (SELECT id FROM address where street = 'Brockhauser Weg' and house_number = '80-82'));

# GARDENA
INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'GARDENA', NOW(), null);

# HH
INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'Harro Höfliger', NOW(), null);

# Keuco
INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'KEUCO', NOW(), null);

# Kurtz
INSERT IGNORE INTO address(city, country, house_number, postcode, street)
VALUES ('Kreuzwertheim', 'Germany', '2', '97892', 'Frankenstraße');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'Kurtz-Ersa', NOW(),
        (SELECT id FROM address where street = 'Frankenstraße' and house_number = '2'));

# Vahle
INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'Vahle', NOW(), null);

# Vaillant
INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key,
                                is_active, name, update_time, address_id)
VALUES (NOW(), 'tmp@nicando.de', 0, true, 'Vaillant', NOW(), null);

# Wolf
INSERT IGNORE INTO address(city, country, house_number, postcode, street)
VALUES ('Mainburg', 'Germany', '1', '84048', 'Industriestraße');