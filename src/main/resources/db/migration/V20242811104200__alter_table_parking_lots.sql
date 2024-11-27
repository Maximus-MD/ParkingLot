ALTER TABLE parking_lots
    ALTER COLUMN start_time DROP NOT NULL;

ALTER TABLE parking_lots
    ALTER COLUMN end_time DROP NOT NULL;