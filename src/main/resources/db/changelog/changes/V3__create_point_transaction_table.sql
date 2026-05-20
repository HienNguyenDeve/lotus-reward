-- liquibase formatted sql

-- changeset hiennguyen:3
CREATE TABLE point_transactions (
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    type VARCHAR(20) NOT NULL,

    point BIGINT NOT NULL,

    balance_before BIGINT NOT NULL,

    balance_after BIGINT NOT NULL,

    reference_type VARCHAR(50),

    reference_id UUID,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_point_transaction_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- index
CREATE INDEX idx_point_transaction_user_id
    ON point_transactions(user_id);

CREATE INDEX idx_point_transaction_created_at
    ON point_transactions(created_at);

CREATE INDEX idx_point_transaction_user_created_at
    ON point_transactions(user_id, created_at);

CREATE INDEX idx_point_transaction_reference
    ON point_transactions(reference_type, reference_id);