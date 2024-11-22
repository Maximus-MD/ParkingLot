ALTER TABLE parking_lots
    DROP COLUMN levels,
    ADD COLUMN temporary_closed BOOLEAN DEFAULT false;
