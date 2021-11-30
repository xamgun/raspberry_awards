DROP TABLE IF EXISTS award;

CREATE TABLE award
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(250),
    studios VARCHAR(250),
    winner VARCHAR(250),
    producers VARCHAR(250),
    year INT
);