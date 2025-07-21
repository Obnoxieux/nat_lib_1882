CREATE TABLE genre(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE library(
    id INT AUTO_INCREMENT NOT NULL ,
    name VARCHAR(255) NOT NULL ,
    PRIMARY KEY (id)
);

CREATE TABLE author(
    id INT AUTO_INCREMENT NOT NULL ,
    name VARCHAR(255) NOT NULL ,
    PRIMARY KEY (id)
);

INSERT INTO genre (name)
SELECT DISTINCT book.genre
FROM book;

UPDATE book
SET book.genre = (SELECT id FROM genre WHERE genre.name = book.genre);

ALTER TABLE book MODIFY genre INT;
ALTER TABLE book ADD FOREIGN KEY (genre) REFERENCES genre(id) ON UPDATE CASCADE ON DELETE SET NULL;

INSERT INTO endowment (name)
SELECT DISTINCT book.endowment
FROM book
WHERE endowment IS NOT NULL ;

UPDATE book
SET book.endowment = (SELECT id FROM endowment WHERE endowment.name = book.endowment);

ALTER TABLE book MODIFY endowment INT;
ALTER TABLE book ADD FOREIGN KEY (endowment) REFERENCES endowment(id) ON UPDATE CASCADE ON DELETE SET NULL;

INSERT INTO author (name)
SELECT DISTINCT book.author
FROM book
WHERE author IS NOT NULL ;

CREATE TABLE authors_books(
    author_id INT NOT NULL,
    book_id INT NOT NULL,
    PRIMARY KEY (author_id, book_id)
);

INSERT INTO authors_books (author_id, book_id)
SELECT a.id, b.id
FROM book AS b
INNER JOIN author AS a ON b.author = a.name;