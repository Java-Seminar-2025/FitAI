-- 1. Kreiranje tablice za korisnike (prema User.java)
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       created_at TIMESTAMP
);

-- 2. Kreiranje tablice za vježbe (prema Exercise.java)
CREATE TABLE exercise (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          muscle_group VARCHAR(255),
                          description VARCHAR(255),
                          equipment VARCHAR(255)
);

-- 3. Ubacivanje vaših testnih podataka u tablicu users
INSERT INTO users (username, email, password, created_at) VALUES ('test1', 'test1@example.com', 'pass123', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password, created_at) VALUES ('test2', 'test2@example.com', 'pass123', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password, created_at) VALUES ('test3', 'test3@example.com', 'pass123', CURRENT_TIMESTAMP);