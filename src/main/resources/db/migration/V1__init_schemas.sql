CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32),
    birth_date DATE
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    header VARCHAR(64),
    description TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT REFERENCES users
);

