CREATE TABLE IF NOT EXISTS users (email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, firstname VARCHAR(100)  NOT NULL,lastname VARCHAR(100)  NOT NULL, language VARCHAR(100)  NOT NULL, permissions int)
create table if not exists courses (title varchar(100), description varchar(100), author varchar(100), students varchar(1000), data bytea)
create table if not exists units (title varchar(100), unitType varchar(100))