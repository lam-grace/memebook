BEGIN TRANSACTION;
set datestyle to ISO,MDY;

DROP SEQUENCE IF EXISTS person_serial, image_serial, meme_serial, tag_serial, team_serial, text_serial CASCADE;
DROP TABLE IF EXISTS image_author, meme_author, image_tag, favourite_image, favourite_meme, person, image, meme, meme_text, tag, team, team_member;

-- region CREATE TABLEs
CREATE SEQUENCE person_serial;
CREATE TABLE person
(
    person_id INT NOT NULL DEFAULT nextval('person_serial'),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL DEFAULT 'Standard',
    active BOOLEAN NOT NULL DEFAULT TRUE,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (person_id)
);

CREATE SEQUENCE image_serial;
CREATE TABLE image
(
  image_id INT NOT NULL DEFAULT nextval('image_serial'),
  file_size INTEGER,
  file_blob BYTEA,
  date_uploaded DATE NOT NULL DEFAULT now(),
  popularity NUMERIC NOT NULL DEFAULT 0,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (image_id)
);


CREATE SEQUENCE meme_serial;
CREATE TABLE meme
(
  meme_id INT NOT NULL DEFAULT nextval('meme_serial'),
  image_id INT NOT NULL,
  date_uploaded DATE NOT NULL DEFAULT now(),
  popularity NUMERIC NOT NULL DEFAULT 0,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  approved BOOLEAN NOT NULL DEFAULT FALSE,
  font_size INT NOT NULL DEFAULT 40,
  font_color VARCHAR(10) NOT NULL DEFAULT '#000000',
  PRIMARY KEY (meme_id),
  FOREIGN KEY (image_id) REFERENCES image(image_id)
);

CREATE SEQUENCE text_serial;
CREATE TABLE meme_text
(
    text_id INT NOT NULL DEFAULT nextval('text_serial'),
    meme_id INT NOT NULL,
    text VARCHAR,
    location_x INT,
    location_y INT,
    PRIMARY KEY (text_id),
    FOREIGN KEY (meme_id) REFERENCES meme(meme_id)
);

CREATE SEQUENCE tag_serial;
CREATE TABLE tag
(
    tag_id INT NOT NULL DEFAULT nextval('tag_serial'),
    tag_name VARCHAR NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (tag_id),
    UNIQUE (tag_name)
);

CREATE TABLE image_author
(
    author_id INT NOT NULL,
    image_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES person(person_id),
    FOREIGN KEY (image_id) REFERENCES image(image_id)
);

CREATE TABLE meme_author
(
    author_id INT NOT NULL,
    meme_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES person(person_id),
    FOREIGN KEY (meme_id) REFERENCES meme(meme_id)
);

CREATE TABLE image_tag
(
    image_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (image_id) REFERENCES image(image_id),
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

CREATE TABLE favourite_image
(
    person_id INT NOT NULL,
    image_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (image_id) REFERENCES image(image_id)
);

CREATE TABLE favourite_meme
(
    person_id INT NOT NULL,
    meme_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (meme_id) REFERENCES meme(meme_id)
);

CREATE SEQUENCE team_serial;
CREATE TABLE team
(
    team_id INT NOT NULL DEFAULT nextval('team_serial'),
    team_name VARCHAR NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (team_id)
);

CREATE TABLE team_member
(
    team_id INT NOT NULL,
    person_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (team_id) REFERENCES team(team_id)
);
-- endregion

-- region INSERT INTO person
INSERT INTO person (username, password, salt) VALUES ('James', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('MemeWizard', 'AEkA+uLpfXgTx8j3PRS7Eg==', 'augXu5LEXWn9ASX146ymcnYyygLcDThBDCvUg8EoBdOkKgtDliqCmjGcUXY5XlehSJrqEslAaoWo9uLm/PvrtUyyDssW667JxkivNWtrxHw2Twax+Ttll+wx6VucFvdHr1kSmw9/X+4TTomQNnbJDko1CLig6g7p3lwIIN9gCAU=');
INSERT INTO person (username, password, role, salt) VALUES ('Mara', 'nao2VpQCrkIHE+djLNbsfw==', 'Admin', 'jp7BOwDLJC3wBAL47Ns7mb1fjMjkTkd1iSHmdx3a79BydoT5ZKK8R76lVJF4GAF4dtvXm6ywCQVwHHvLAePvgD8HLAXfOQ115RBadtnboF8lwep9al+B+6/raX/FCyER5WcXqndY5p+uDh3uy3trAfCcEWKksBQnfdKlWkVjl84=');
INSERT INTO person (username, password, role, salt) VALUES ('Zach', 'niCwSl9PKe7b2n6//cDU6A==', 'Admin', 'evmcKCWIwYdM7dDE6IU9w4tU1rplX3vsbDHZs72b81nMMnjbm4ZyhWhDv3bU7rush3sJp33RHrBzvdMK7G0DoQRrAoDm6K0PZRf2M/GY2rxTbyKGAOcsaaBi75+zXhBUdMDAk42eds+wVx8RMKXVVtJtUwRNcfN2a3mKClnqyeM=');
INSERT INTO person (username, password, role, salt) VALUES ('Grace', 's9+MGo29qcnPV+PtxeldUA==', 'Admin', '+zZkgaN9rRM+uOgFsr6QS8E7S8YNezrDbDpsMM1cxX1kTqK3ukd0WjpCI2D79gF7YDURiDIe098KxgyjZCB0s72cZ8MkbRikx1YgmPnBlM6HKtN/igihz2ldQYiojOCGK6Ys7aww4E1mPRI0M8KUdqXCAFyvUmpT0zyXCos40nM=');
INSERT INTO person (username, password, role, salt) VALUES ('Julie', 'gx4IZm5VDuEtXHP2nv8z0g==', 'Admin', '81m6wQkoQbcanoEzCrOuKjU1jId0ZCYeAlBQoR+fcv5SxZ9PYGQAh+4/n7FCP050MDk8kt2j6eZn4wcnAKDNhMsm00p0ZA00awYLDjo3LzXSy4xut0OEKFLJUg7JYL+zVmArxddo/Z/CbaYoJ0ODaQKeMh+v7h/VET53sahedb8=');
INSERT INTO person (username, password, role, salt) VALUES ('Runa', 'Ha+v+P9JVoxoJhGFvdEtmQ==', 'Admin', '0S32bifQtnTCTgXMLF0sxaVIRE2eDdcXWjOKzoniHcxaaLWWHWovoqx1KqOU2p0eOCwcgyH0ofiblTag6u2zDJIUsmCdj3K9m7Y4tNQ+EizZ+BJ0h6XEOYG60hPDmJL0rw6EwUg7zV1mARo1eMkFj5wdMkEd8ZXp/wQT4IeObk0=');
INSERT INTO person (username, password, salt) VALUES ('popcorn', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('MemeQueenSupreme', 'AEkA+uLpfXgTx8j3PRS7Eg==', 'augXu5LEXWn9ASX146ymcnYyygLcDThBDCvUg8EoBdOkKgtDliqCmjGcUXY5XlehSJrqEslAaoWo9uLm/PvrtUyyDssW667JxkivNWtrxHw2Twax+Ttll+wx6VucFvdHr1kSmw9/X+4TTomQNnbJDko1CLig6g7p3lwIIN9gCAU=');
INSERT INTO person (username, password, salt) VALUES ('Odditude', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('the-lizard-king', 'AEkA+uLpfXgTx8j3PRS7Eg==', 'augXu5LEXWn9ASX146ymcnYyygLcDThBDCvUg8EoBdOkKgtDliqCmjGcUXY5XlehSJrqEslAaoWo9uLm/PvrtUyyDssW667JxkivNWtrxHw2Twax+Ttll+wx6VucFvdHr1kSmw9/X+4TTomQNnbJDko1CLig6g7p3lwIIN9gCAU=');
INSERT INTO person (username, password, salt) VALUES ('sPoNgEbOb', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('qwertyyy', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('MemeBoi', 'AEkA+uLpfXgTx8j3PRS7Eg==', 'augXu5LEXWn9ASX146ymcnYyygLcDThBDCvUg8EoBdOkKgtDliqCmjGcUXY5XlehSJrqEslAaoWo9uLm/PvrtUyyDssW667JxkivNWtrxHw2Twax+Ttll+wx6VucFvdHr1kSmw9/X+4TTomQNnbJDko1CLig6g7p3lwIIN9gCAU=');
INSERT INTO person (username, password, salt) VALUES ('StarWarsFan99', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
INSERT INTO person (username, password, salt) VALUES ('AGGRESIVE-CAPSLOCK', 'AEkA+uLpfXgTx8j3PRS7Eg==', 'augXu5LEXWn9ASX146ymcnYyygLcDThBDCvUg8EoBdOkKgtDliqCmjGcUXY5XlehSJrqEslAaoWo9uLm/PvrtUyyDssW667JxkivNWtrxHw2Twax+Ttll+wx6VucFvdHr1kSmw9/X+4TTomQNnbJDko1CLig6g7p3lwIIN9gCAU=');
INSERT INTO person (username, password, salt) VALUES ('Frank', 'o2GSzNquZmo3TYhJZaD2XQ==', '/Ycwx2Edxr34Wup8E8uF/rXXiREnFt7oimoyqz69okUZQFYBNSfrfgP3/QHrJ+CRgYq+Ts0798NpK9P5Cas8tDd8qOs8P8q8TsDbLy2n0RPqlZDTXGd4jMRH0NaW+i8E93eMm4BB1Mah4M+SochkhDCbrFk3p8CF/VrhCcisCes=');
-- endregion

-- region INSERT INTO tag
INSERT INTO tag (tag_name) VALUES ('code');
INSERT INTO tag (tag_name) VALUES ('food');
INSERT INTO tag (tag_name) VALUES ('coffee');
INSERT INTO tag (tag_name) VALUES ('fire');
INSERT INTO tag (tag_name) VALUES ('animal');
INSERT INTO tag (tag_name) VALUES ('signs');
INSERT INTO tag (tag_name) VALUES ('choices');
INSERT INTO tag (tag_name) VALUES ('sweating');
INSERT INTO tag (tag_name) VALUES ('scary');
INSERT INTO tag (tag_name) VALUES ('funny');
INSERT INTO tag (tag_name) VALUES ('shocked');
INSERT INTO tag (tag_name) VALUES ('surprised');
INSERT INTO tag (tag_name) VALUES ('birthday');
INSERT INTO tag (tag_name) VALUES ('party');
INSERT INTO tag (tag_name) VALUES ('cartoon');
INSERT INTO tag (tag_name) VALUES ('sad');
INSERT INTO tag (tag_name) VALUES ('lonely');
INSERT INTO tag (tag_name) VALUES ('TV show');
-- endregion

-- region INSERT INTO image
INSERT INTO image (file_size, file_blob, popularity) VALUES (63,pg_read_binary_file('buttons.png')::bytea, 3);
INSERT INTO image (file_size, file_blob, popularity) VALUES (67,pg_read_binary_file('buzz.jpg')::bytea, 5);
INSERT INTO image (file_size, file_blob, popularity) VALUES (195,pg_read_binary_file('fine.jpg')::bytea, 6);
INSERT INTO image (file_size, file_blob, popularity) VALUES (317,pg_read_binary_file('doge.png')::bytea, 4);
INSERT INTO image (file_size, file_blob, popularity) VALUES (202,pg_read_binary_file('futurama.png')::bytea, 4);
INSERT INTO image (file_size, file_blob, popularity) VALUES (281,pg_read_binary_file('kermit.png')::bytea, 6);
INSERT INTO image (file_size, file_blob) VALUES (129,pg_read_binary_file('looking.jpeg')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (188,pg_read_binary_file('monkey.png')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (66,pg_read_binary_file('narcos.jpg')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (198,pg_read_binary_file('pikachu.png')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (30,pg_read_binary_file('simply.jpg')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (65,pg_read_binary_file('spiderman.png')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (41,pg_read_binary_file('spongebob.jpg')::bytea);
INSERT INTO image (file_size, file_blob) VALUES (148,pg_read_binary_file('tom.png')::bytea);

-- endregion

-- region INSERT INTO team
INSERT INTO team (team_name) VALUES ('Meme Team');
INSERT INTO team (team_name) VALUES ('Brewery Finder');
INSERT INTO team (team_name) VALUES ('Events Team');
INSERT INTO team (team_name) VALUES ('Here for Seconds');
INSERT INTO team (team_name) VALUES ('Family Reader');
INSERT INTO team (team_name) VALUES ('Football Fanatics');
INSERT INTO team (team_name) VALUES ('Silly Memers');
-- endregion

-- region INSERT INTO team_member
INSERT INTO team_member (team_id, person_id) VALUES (1,2);
INSERT INTO team_member (team_id, person_id) VALUES (1,3);
INSERT INTO team_member (team_id, person_id) VALUES (1,4);
INSERT INTO team_member (team_id, person_id) VALUES (1,5);
INSERT INTO team_member (team_id, person_id) VALUES (1,6);
INSERT INTO team_member (team_id, person_id) VALUES (1,7);
INSERT INTO team_member (team_id, person_id) VALUES (2,1);
INSERT INTO team_member (team_id, person_id) VALUES (3,1);
INSERT INTO team_member (team_id, person_id) VALUES (4,1);
INSERT INTO team_member (team_id, person_id) VALUES (5,1);
INSERT INTO team_member (team_id, person_id) VALUES (3,7);
-- endregion

-- region INSERT INTO meme
INSERT INTO meme (image_id, approved, popularity, font_color, font_size) VALUES (13,TRUE,5,'#FF0000',40);
INSERT INTO meme (image_id, approved, popularity, font_color, font_size) VALUES (2,TRUE,9,'#FFFFFF',65);
INSERT INTO meme (image_id, approved, popularity, font_color, font_size) VALUES (5,TRUE,17,'#00FF00',45);
INSERT INTO meme (image_id, approved, popularity, font_size, font_color) VALUES (11,TRUE,4,40,'#FFFFEE');
INSERT INTO meme (image_id, font_color, font_size) VALUES (10,'#000000',45);
INSERT INTO meme (image_id, font_color, font_size) VALUES (6,'#00FF00',40);
INSERT INTO meme (image_id, approved, popularity,font_color) VALUES (9,TRUE,2,'#00FF00');
INSERT INTO meme (image_id,font_color) VALUES (13,'#800080');
INSERT INTO meme (image_id,font_color,font_size) VALUES (3,'#FFFFFF',36);
INSERT INTO meme (image_id, approved, popularity) VALUES (10,TRUE,7);
INSERT INTO meme (image_id, approved, popularity,font_color) VALUES (4,TRUE,0,'#00FFFF');

-- endregion

-- region INSERT INTO meme_text
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (1,'LeT uS cOdE',22,2);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (1,'a MeMeGeNeRaToR',12,16);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (2,'Legacy code.',32,5);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (2,'Legacy code everywhere.',1,80);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (3,'Not sure if fixing issues',4,5);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (3,'or creating them',17,80);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (4,'One does not simply',2,2);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (4,'make a meme',2,12);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (4,'without a meme generator!',2,80);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (5,'Me, realising',2,15);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (5,'my code works:',2,25);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (6,'Join me on the dark side',3,0);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (6,'We have IntelliSense!',9,80);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (7,'Rob when asking questions in class',1,50);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (8,'{iNaPpRoPrIaTe cOnTeNt}',1,5);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (9,'Final Capstone Project',1,0);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (10,'Me: I will remember',1,10);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (10,'Also me: *forgets*',1,20);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (11,'WOW!',15,10);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (11,'Cool',65,35);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (11,'funny',50,60);
INSERT INTO meme_text (meme_id, text, location_x, location_y) VALUES (11,'So much memes!',1,85);


-- endregion

-- region INSERT INTO image_author
INSERT INTO image_author (author_id, image_id) VALUES (3,1);
INSERT INTO image_author (author_id, image_id) VALUES (3,2);
INSERT INTO image_author (author_id, image_id) VALUES (3,3);
INSERT INTO image_author (author_id, image_id) VALUES (5,4);
INSERT INTO image_author (author_id, image_id) VALUES (2,5);
INSERT INTO image_author (author_id, image_id) VALUES (2,6);
INSERT INTO image_author (author_id, image_id) VALUES (7,7);
INSERT INTO image_author (author_id, image_id) VALUES (2,8);
INSERT INTO image_author (author_id, image_id) VALUES (6,9);
INSERT INTO image_author (author_id, image_id) VALUES (6,10);
INSERT INTO image_author (author_id, image_id) VALUES (7,11);
INSERT INTO image_author (author_id, image_id) VALUES (4,12);
INSERT INTO image_author (author_id, image_id) VALUES (4,13);
INSERT INTO image_author (author_id, image_id) VALUES (3,14);

-- endregion

-- region INSERT INTO meme_author
INSERT INTO meme_author (author_id, meme_id) VALUES (1,1);
INSERT INTO meme_author (author_id, meme_id) VALUES (3,2);
INSERT INTO meme_author (author_id, meme_id) VALUES (5,3);
INSERT INTO meme_author (author_id, meme_id) VALUES (7,4);
INSERT INTO meme_author (author_id, meme_id) VALUES (1,5);
INSERT INTO meme_author (author_id, meme_id) VALUES (5,6);
INSERT INTO meme_author (author_id, meme_id) VALUES (2,7);
INSERT INTO meme_author (author_id, meme_id) VALUES (4,8);
INSERT INTO meme_author (author_id, meme_id) VALUES (6,9);
INSERT INTO meme_author (author_id, meme_id) VALUES (2,10);
INSERT INTO meme_author (author_id, meme_id) VALUES (4,11);
-- endregion

-- region INSERT INTO image_tag
INSERT INTO image_tag (image_id, tag_id) VALUES (1,8);
INSERT INTO image_tag (image_id, tag_id) VALUES (1,7);
INSERT INTO image_tag (image_id, tag_id) VALUES (2,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (3,3);
INSERT INTO image_tag (image_id, tag_id) VALUES (3,4);
INSERT INTO image_tag (image_id, tag_id) VALUES (3,5);
INSERT INTO image_tag (image_id, tag_id) VALUES (4,5);
INSERT INTO image_tag (image_id, tag_id) VALUES (5,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (5,7);
INSERT INTO image_tag (image_id, tag_id) VALUES (6,10);
INSERT INTO image_tag (image_id, tag_id) VALUES (6,5);
INSERT INTO image_tag (image_id, tag_id) VALUES (7,7);
INSERT INTO image_tag (image_id, tag_id) VALUES (7,8);
INSERT INTO image_tag (image_id, tag_id) VALUES (8,12);
INSERT INTO image_tag (image_id, tag_id) VALUES (8,5);
INSERT INTO image_tag (image_id, tag_id) VALUES (9,16);
INSERT INTO image_tag (image_id, tag_id) VALUES (9,17);
INSERT INTO image_tag (image_id, tag_id) VALUES (9,18);
INSERT INTO image_tag (image_id, tag_id) VALUES (10,5);
INSERT INTO image_tag (image_id, tag_id) VALUES (10,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (10,12);
INSERT INTO image_tag (image_id, tag_id) VALUES (11,18);
INSERT INTO image_tag (image_id, tag_id) VALUES (12,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (13,10);
INSERT INTO image_tag (image_id, tag_id) VALUES (13,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (14,10);
INSERT INTO image_tag (image_id, tag_id) VALUES (14,15);
INSERT INTO image_tag (image_id, tag_id) VALUES (14,5);
-- endregion

-- region INSERT INTO favourite_image
INSERT INTO favourite_image (person_id, image_id) VALUES (1,1);
INSERT INTO favourite_image (person_id, image_id) VALUES (2,2);
INSERT INTO favourite_image (person_id, image_id) VALUES (3,3);
INSERT INTO favourite_image (person_id, image_id) VALUES (4,4);
INSERT INTO favourite_image (person_id, image_id) VALUES (1,3);
INSERT INTO favourite_image (person_id, image_id) VALUES (2,4);
INSERT INTO favourite_image (person_id, image_id) VALUES (3,5);
INSERT INTO favourite_image (person_id, image_id) VALUES (4,6);
INSERT INTO favourite_image (person_id, image_id) VALUES (5,3);
INSERT INTO favourite_image (person_id, image_id) VALUES (5,4);
INSERT INTO favourite_image (person_id, image_id) VALUES (6,5);
INSERT INTO favourite_image (person_id, image_id) VALUES (7,6);
INSERT INTO favourite_image (person_id, image_id) VALUES (6,1);
INSERT INTO favourite_image (person_id, image_id) VALUES (7,2);
-- endregion

-- region INSERT INTO favourite_meme
INSERT INTO favourite_meme (person_id, meme_id) VALUES (1,1);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (7,1);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (6,1);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (2,1);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (4,1);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (1,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (2,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (3,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (4,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (5,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (6,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (7,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (8,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (9,2);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (1,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (2,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (3,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (4,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (5,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (6,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (7,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (8,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (9,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (10,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (11,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (12,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (13,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (14,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (15,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (16,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (17,3);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (1,4);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (4,4);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (5,4);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (6,4);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (5,7);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (10,7);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (5,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (6,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (7,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (13,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (14,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (15,10);
INSERT INTO favourite_meme (person_id, meme_id) VALUES (16,10);
-- endregion

COMMIT;