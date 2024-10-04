CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    recipient VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    notification_type VARCHAR(50) NOT NULL CHECK (notification_type IN ('SMS', 'EMAIL','WHATSAPP')),
    notification_status VARCHAR(50) NOT NULL CHECK (notification_status IN ('FAILED', 'SUCCESS', 'IN_PROGRESS')),
    channel_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL ,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE notifications
ADD CONSTRAINT fk_notifications_channels
FOREIGN KEY (channel_id) REFERENCES channels(id);
