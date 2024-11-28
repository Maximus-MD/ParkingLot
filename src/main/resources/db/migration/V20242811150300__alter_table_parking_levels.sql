ALTER TABLE parking_levels
    DROP CONSTRAINT fk_parking_levels_to_parking_lots;

ALTER TABLE parking_levels
    ADD CONSTRAINT fk_parking_levels_to_parking_lots
        FOREIGN KEY (parking_lot_id)
            REFERENCES parking_lots (parking_lot_id)
            ON DELETE CASCADE;
