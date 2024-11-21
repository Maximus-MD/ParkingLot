CREATE TABLE working_time (
    working_time_id SERIAL PRIMARY KEY,
    parking_lot_id INT NOT NULL,
    day_name days_of_week NOT NULL,
    CONSTRAINT fk_working_time_to_parking_lots FOREIGN KEY (parking_lot_id) REFERENCES parking_lots (parking_lot_id)
);
