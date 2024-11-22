create table parking_lot_days
(
    parking_lot_id INT NOT NULL,
    day_id         INT NOT NULL,
    CONSTRAINT fk_parking_lots FOREIGN KEY (parking_lot_id) REFERENCES parking_lots (parking_lot_id),
    CONSTRAINT fk_working_days FOREIGN KEY (day_id) REFERENCES working_days (day_id),
    PRIMARY KEY (parking_lot_id, day_id)
);