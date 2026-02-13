CREATE TABLE device (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    brand VARCHAR(255),
    state VARCHAR(50),
    creation_time TIMESTAMP NOT NULL
);
