CREATE TABLE ROLES (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(15),
    CONSTRAINT chk_role CHECK (role_name IN ('ROLE_ADMIN', 'ROLE_REGULAR'))
);