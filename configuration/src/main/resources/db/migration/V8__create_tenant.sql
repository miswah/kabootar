CREATE TABLE tenant (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    tenant_desc VARCHAR(255) NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_tenant PRIMARY KEY (id),
    CONSTRAINT uk_tenant_revision_key UNIQUE (revision_id),
    CONSTRAINT fk_tenant_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_tenant_enabled ON tenant(revision_id, enabled);
