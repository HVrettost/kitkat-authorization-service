CREATE TABLE AUTH_ROLE_TO_PERMISSIONS(
    ID UUID PRIMARY KEY,
    ROLE VARCHAR(20) NOT NULL,
    PERMISSIONS VARCHAR NOT NULL
);

INSERT INTO AUTH_ROLE_TO_PERMISSIONS(ID, ROLE, PERMISSIONS) VALUES('091409b8-30c0-46df-aa1f-419fdd4583ee', 'ROLE_FREE_USER', 'UPLOAD_IMAGE_PERMISSION UPLOAD_VIDEO_PERMISSION');