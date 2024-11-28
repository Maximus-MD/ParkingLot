ALTER TABLE parking_spots
    DROP CONSTRAINT fk_parking_spots_to_parking_levels;

ALTER TABLE parking_spots
    ADD CONSTRAINT fk_parking_spots_to_parking_levels
        FOREIGN KEY (level_id)
            REFERENCES parking_levels (level_id)
            ON DELETE CASCADE;