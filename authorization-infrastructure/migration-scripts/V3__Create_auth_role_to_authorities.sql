CREATE TABLE AUTH_ROLE_TO_AUTHORITIES(
    ROLE VARCHAR(20) PRIMARY KEY NOT NULL,
    AUTHORITIES VARCHAR NOT NULL
);

INSERT INTO AUTH_ROLE_TO_AUTHORITIES(ROLE, AUTHORITIES) VALUES ('ROLE_FREE_USER', 'REFRESH_TOKEN_DELETE REFRESH_TOKEN_ALL_DELETE')
