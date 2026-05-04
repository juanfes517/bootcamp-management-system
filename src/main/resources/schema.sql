CREATE TABLE IF NOT EXISTS technology (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS capability (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS technology_capability (
    id BIGSERIAL PRIMARY KEY,
    technology_id BIGSERIAL NOT NULL REFERENCES technology(id),
    capability_id BIGSERIAL NOT NULL REFERENCES capability(id)
);

CREATE TABLE IF NOT EXISTS bootcamp (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(90) NOT NULL,
    release_date TIMESTAMP NOT NULL,
    duration_days INT NOT NULL
);

CREATE TABLE IF NOT EXISTS capability_bootcamp (
    id BIGSERIAL PRIMARY KEY,
    capability_id BIGSERIAL NOT NULL REFERENCES capability(id),
    bootcamp_id BIGSERIAL NOT NULL REFERENCES bootcamp(id)
);
