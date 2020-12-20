CREATE TABLE AUTH_ROLE_TO_AUTHORITIES(
    ROLE VARCHAR(20) NOT NULL,
    AUTHORITIES VARCHAR NOT NULL,
    CONSTRAINT PRIMARY_KEY_ARTA_ROLE PRIMARY KEY (ROLE),
    CONSTRAINT FOREIGN_KEY_ROLE FOREIGN KEY (ROLE) REFERENCES AUTH_ROLE (ROLE) ON DELETE CASCADE
);

INSERT INTO AUTH_ROLE_TO_AUTHORITIES(ROLE, AUTHORITIES) VALUES ('ROLE_FREE_USER', 'REFRESH_TOKEN_DELETE REFRESH_TOKEN_ALL_DELETE');
