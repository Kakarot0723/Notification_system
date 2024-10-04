CREATE TABLE channels (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    channel_status VARCHAR(50) NOT NULL CHECK (channel_status IN ('ENABLED', 'DISABLED')),
    channel_key VARCHAR(255) NOT NULL UNIQUE,
    notification_type VARCHAR(50) NOT NULL,
    notification_status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_channel_key ON channels(channel_key);