CREATE TABLE AUTH_ROLE(
    ID UUID NOT NULL DEFAULT uuid_generate_v4(),
    ROLE VARCHAR(20) NOT NULL,
    CONSTRAINT PRIMARY_KEY_AR_ID PRIMARY KEY (ID)
);

INSERT INTO AUTH_ROLE (ID, ROLE) VALUES ('b7134137-7cea-4b10-af4a-ce90b78bac16', 'ROLE_FREE_USER');