CREATE DATABASE hw17;

CREATE TABLE events (
                        id BIGSERIAL PRIMARY KEY ,
                        title VARCHAR(50),
                        location VARCHAR(200),
                        capacity INTEGER,
                        reserved_count INTEGER DEFAULT 0,
                        ticket_price DECIMAL(15,2),
                        status VARCHAR DEFAULT 'ACTIVE'
);

CREATE TABLE reservations (
                              id BIGSERIAL PRIMARY KEY ,
                              customer_name VARCHAR(50),
                              customer_phone VARCHAR(15),
                              event_id BIGINT,
                              ticket_count INTEGER,
                              reservation_date DATE,
                              status VARCHAR
);