ALTER TABLE users ALTER COLUMN username SET NOT NULL;
ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ALTER COLUMN password SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT uq_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

-- user role: USER, ADMIN
ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER' NOT NULL;

-- entities for password reset and email verification
ALTER TABLE exercises ALTER COLUMN description SET DATA TYPE VARCHAR(1000);

-- test user password hashing (password: pass123)
UPDATE users SET password = '$2a$10$WmUfGrt5wTV8Q8J6lA31g.82tAI64w/3GUc6JN2DJGzHbZuQHMzCW';
UPDATE users SET role = 'ADMIN' WHERE username = 'test1';