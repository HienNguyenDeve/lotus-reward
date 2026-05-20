-- Liquibase fomartted SQL

-- changeset nguyenhien:1
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    avatar_url TEXT,
    lotus_point BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- index
CREATE INDEX idx_users_username
    ON users(username);

CREATE INDEX idx_users_created_at
    ON users(created_at);

