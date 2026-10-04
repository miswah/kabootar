CREATE TABLE configuration_revision (
    id CHAR(36) NOT NULL,
    revision_number BIGINT NOT NULL,
    status VARCHAR(32) NOT NULL,
    based_on_revision_id CHAR(36) NULL,
    schema_version VARCHAR(32) NOT NULL DEFAULT '1',
    checksum CHAR(64) NULL,
    lock_version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published_by VARCHAR(255) NULL,
    published_at TIMESTAMP(6) NULL,
    CONSTRAINT pk_configuration_revision PRIMARY KEY (id),
    CONSTRAINT uk_configuration_revision_number UNIQUE (revision_number),
    CONSTRAINT ck_configuration_revision_status CHECK (status IN ('DRAFT','PUBLISHED','DISCARDED')),
    CONSTRAINT ck_configuration_revision_lock_version CHECK (lock_version >= 0),
    CONSTRAINT fk_configuration_revision_base FOREIGN KEY (based_on_revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_configuration_revision_status ON configuration_revision(status);
CREATE INDEX idx_configuration_revision_created_at ON configuration_revision(created_at);
CREATE INDEX idx_configuration_revision_base ON configuration_revision(based_on_revision_id);
