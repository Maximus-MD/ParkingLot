ALTER TABLE users DROP CONSTRAINT chk_pass_size;

ALTER TABLE users
    ADD CONSTRAINT chk_pass_size CHECK (LENGTH(password) >= 5);