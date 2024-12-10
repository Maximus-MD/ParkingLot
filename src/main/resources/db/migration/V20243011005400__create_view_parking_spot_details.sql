CREATE VIEW parking_spot_details AS
SELECT
    ps.spot_id,
    ps.name AS spot_name,
    ps.occupied,
    ps.type AS spot_type,
    ps.user_id,
    pl.level_id,
    pl.name AS level_name,
    lot.parking_lot_id,
    lot.name AS parking_name
FROM parking_spots ps
         JOIN parking_levels pl ON ps.level_id = pl.level_id
         JOIN parking_lots lot ON pl.parking_lot_id = lot.parking_lot_id;