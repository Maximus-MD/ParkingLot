ALTER TABLE parking_spots
    ADD COLUMN user_id INTEGER,
       ADD CONSTRAINT fk_user_id
            FOREIGN KEY(user_id)
                REFERENCES USERS(user_id);