CREATE TABLE parking_lot_users (
    user_id BIGINT NOT NULL,
    parking_lot_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, parking_lot_id),
    FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(parking_lot_id) ON DELETE CASCADE
)