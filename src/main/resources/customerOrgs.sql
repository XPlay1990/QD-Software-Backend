INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Lüdenscheid', 'Germany', '80-82', '58507', 'Brockhauser Weg');

INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'ERCO',
           (SELECT id FROM address where street = 'Brockhauser Weg' and house_number = '80-82'));

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'GARDENA');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'Harro Höfliger');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'KEUCO');

INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Kreuzwertheim', 'Germany', '2', '97892', 'Frankenstraße');

INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'Kurtz-Ersa',
           (SELECT id FROM address where street = 'Frankenstraße' and house_number = '2'));

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'Vahle');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'Vaillant');

INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Mainburg', 'Germany', '1', '84048', 'Industriestraße');

INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@nicando.de', 0, true, true, 'Wolf',
           (SELECT id FROM address where street = 'Industriestraße' and house_number = '1'));