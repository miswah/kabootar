CREATE TABLE configuration_snapshot (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    schema_version VARCHAR(32) NOT NULL,
    checksum CHAR(64) NOT NULL,
    regions LONGTEXT NULL,
    services LONGTEXT NULL,
    service_instances LONGTEXT NULL,
    routes LONGTEXT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_configuration_snapshot PRIMARY KEY (id),
    CONSTRAINT uk_configuration_snapshot_revision UNIQUE (revision_id),
    CONSTRAINT fk_configuration_snapshot_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT,
    CONSTRAINT ck_configuration_snapshot_checksum CHECK (CHAR_LENGTH(checksum) = 64)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_configuration_snapshot_checksum ON configuration_snapshot(checksum);
