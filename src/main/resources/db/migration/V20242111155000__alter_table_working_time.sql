ALTER TABLE working_time
    RENAME TO working_days;

ALTER TABLE working_days
    RENAME COLUMN working_time_id TO day_id;

ALTER TABLE working_days
    ALTER COLUMN day_name TYPE VARCHAR(10);

ALTER TABLE working_days
    DROP CONSTRAINT fk_working_time_to_parking_lots;

ALTER TABLE working_days
    DROP COLUMN parking_lot_id;

