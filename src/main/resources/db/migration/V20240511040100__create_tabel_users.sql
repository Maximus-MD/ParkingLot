CREATE TABLE USERS (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(10) NOT NULL,
    phone VARCHAR(9) UNIQUE NOT NULL,
    role_id INTEGER,
    CONSTRAINT email_format CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT phone_format CHECK (phone ~ '^0[67][0-9]{7}$'),
    CONSTRAINT chk_pass_size CHECK (LENGTH(password) >= 5 AND LENGTH(password) <= 10),
    CONSTRAINT fk_role_id
        FOREIGN KEY(role_id)
            REFERENCES ROLES(role_id)
);