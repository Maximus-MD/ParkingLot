ALTER TABLE parking_levels
    DROP COLUMN floor,
    DROP COLUMN total_spots,
    ADD COLUMN name VARCHAR(1);
