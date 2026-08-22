CREATE TABLE message (
    id BIGSERIAL PRIMARY KEY,
    telegram_user_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    message_text VARCHAR(255) NOT NULL,
    message_received_time DATE NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    product_name VARCHAR(255),
    is_expense BOOLEAN NOT NULL,
    category VARCHAR(255),
    payment_method VARCHAR(255),
    payment_point VARCHAR(255)
);
