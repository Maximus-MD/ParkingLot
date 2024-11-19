CREATE TABLE parking_lots (
    parking_lot_id SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL UNIQUE,
    address VARCHAR(70) NOT NULL UNIQUE,
    levels  INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    operates_non_stop BOOLEAN DEFAULT false
);
