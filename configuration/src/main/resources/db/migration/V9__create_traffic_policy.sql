CREATE TABLE traffic_policy (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    strategy VARCHAR(64) NOT NULL,
    configuration LONGTEXT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_traffic_policy PRIMARY KEY (id),
    CONSTRAINT uk_traffic_policy_revision_id UNIQUE (revision_id, id),
    CONSTRAINT fk_traffic_policy_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_traffic_policy_enabled ON traffic_policy(revision_id, enabled);
