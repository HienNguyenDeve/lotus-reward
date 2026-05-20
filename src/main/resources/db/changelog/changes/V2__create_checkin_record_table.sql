-- liquibase formatted sql

-- changeset hiennguyen:2
CREATE TABLE checkin_records (
    id UUID PRIMARY KEY,
    
    user_id UUID NOT NULL,

    checkin_date DATE NOT NULL,

    reward_point INTEGER NOT NULL,

    checkin_order_in_month INTEGER NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_checkin_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT uk_user_checkin_date
        UNIQUE (user_id, checkin_date)
);

-- index
CREATE INDEX idx_checkin_user_id
    ON checkin_records(user_id);

CREATE INDEX idx_checkin_user_created_at
    ON checkin_records(user_id, created_at);

CREATE INDEX idx_checkin_date
    ON checkin_records(checkin_date);