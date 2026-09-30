CREATE TABLE security_policy (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    authentication_required BOOLEAN NOT NULL DEFAULT TRUE,
    ssl_enaabled TINYINT(1)   NOT NULL DEFAULT 1,
    allowed_origins LONGTEXT NULL,
    configuration LONGTEXT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_security_policy PRIMARY KEY (id),
    CONSTRAINT uk_security_policy_revision_id UNIQUE (revision_id, id),
    CONSTRAINT fk_security_policy_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_security_policy_enabled ON security_policy(revision_id, enabled);
