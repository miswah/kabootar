CREATE TABLE IF NOT EXISTS 'region' (
    'id' VARCHAR NOT NULL PRIMARY KEY,
    'key' VARCHAR NOT NULL,
    'created_at' VARCHAR,
    'updated_at' VARCHAR
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS 'service_instance' (
    'id' VARCHAR NOT NULL PRIMARY KEY,
    'key' VARCHAR NOT NULL,
    'service_id' VARCHAR NOT NULL FOREIGN KEY,
    'region_id' VARCHAR NOT NULL FOREIGN KEY,
    'created_at' VARCHAR,
    'updated_at' VARCHAR
)

