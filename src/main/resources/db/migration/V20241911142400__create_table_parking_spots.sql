CREATE TABLE parking_spots (
    spot_id SERIAL PRIMARY KEY,
    level_id INT NOT NULL,
    name VARCHAR(5) NOT NULL,
    state BOOLEAN NOT NULL DEFAULT true,
    type VARCHAR(50) NOT NULL,
    CONSTRAINT  fk_parking_spots_to_parking_levels FOREIGN KEY (level_id) REFERENCES parking_levels (level_id)
);
