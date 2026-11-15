CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       created_at TIMESTAMP
);

CREATE TABLE exercises (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          muscle_group VARCHAR(255),
                          description VARCHAR(255),
                          equipment VARCHAR(255)
);
CREATE TABLE fitness_profiles(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL UNIQUE REFERENCES users(id),
                        experience_level VARCHAR(255),
                        activity_level VARCHAR(255),
                        training_days INT
);
CREATE TABLE workout_plans(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL REFERENCES users(id),
                        name VARCHAR(255) NOT NULL,
                        user_generated BOOLEAN
);
CREATE TABLE workouts(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    plan_id INT NOT NULL REFERENCES workout_plans(id),
                    name VARCHAR(255) NOT NULL,
                    day VARCHAR(255)
);
CREATE TABLE workout_exercises(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    workout_id INT NOT NULL REFERENCES workouts(id),
                    exercise_id INT NOT NULL REFERENCES exercises(id),
                    sets INT,
                    reps INT
);
CREATE TABLE workouts_history(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL REFERENCES users(id),
                    workout_id INT NOT NULL REFERENCES workouts(id),
                    exercise_id INT NOT NULL REFERENCES exercises(id),
                    completed_at TIMESTAMP,
                    duration INT,
                    notes VARCHAR(1000)
);
INSERT INTO users (username, email, password, created_at) VALUES ('test1', 'test1@example.com', 'pass123', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password, created_at) VALUES ('test2', 'test2@example.com', 'pass123', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password, created_at) VALUES ('test3', 'test3@example.com', 'pass123', CURRENT_TIMESTAMP);