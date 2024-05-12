CREATE TABLE users_profiles (
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    profile_id uuid NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);