CREATE TABLE IF NOT EXISTS service_instance (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    `key` VARCHAR(255) NOT NULL,
    service_id VARCHAR(255) NOT NULL,
    region_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_service_instance_service
        FOREIGN KEY (service_id) REFERENCES services(id),

    CONSTRAINT fk_service_instance_region
        FOREIGN KEY (region_id) REFERENCES region(id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;