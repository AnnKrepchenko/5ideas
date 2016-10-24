CREATE TABLE ideas
    (_id INTEGER PRIMARY KEY  UNIQUE,
    name TEXT,
    description TEXT,
    picture TEXT);

INSERT INTO 'ideas'(name,description) VALUES("name", "This is a description");