CREATE TABLE parking_levels (
    level_id SERIAL PRIMARY KEY,
    parking_lot_id INT NOT NULL,
    floor INT NOT NULL,
    total_spots INT NOT NULL,
    CONSTRAINT fk_parking_levels_to_parking_lots FOREIGN KEY (parking_lot_id) REFERENCES parking_lots (parking_lot_id)
);
