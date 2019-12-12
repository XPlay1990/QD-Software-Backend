INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Berlin', 'Germany', '1', '12169', 'Customer Street');
INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 1',
           (SELECT id FROM address where street = 'Customer street' and house_number = '1'));

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 2');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 3');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 4');

INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Berlin', 'Germany', '1', '12169', 'Alexanderplatz');
INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 5',
           (SELECT id FROM address where street = 'Alexanderplatz' and house_number = '1'));

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 6');

INSERT IGNORE INTO organization(creation_time, fall_back_email, identifier_key, is_active, is_customer, name)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 7');

INSERT IGNORE INTO address(city, country, house_number, postcode, street)
    VALUE ('Pirmasens', 'Germany', '12', '66955', 'Straße');

INSERT IGNORE INTO organization( creation_time, fall_back_email, identifier_key, is_active, is_customer, name
                               , address_id)
    VALUE (NOW(), 'tmp@qd-software.de', 0, true, true, 'Customer 8',
           (SELECT id FROM address where street = 'Straße' and house_number = '12'));