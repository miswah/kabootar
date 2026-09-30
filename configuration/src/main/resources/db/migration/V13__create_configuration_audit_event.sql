CREATE TABLE configuration_audit_event (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    event VARCHAR(64) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    metadata LONGTEXT NULL,
    CONSTRAINT pk_configuration_audit_event PRIMARY KEY (id),
    CONSTRAINT fk_configuration_audit_event_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_configuration_audit_event_revision_time ON configuration_audit_event(revision_id, created_at);
CREATE INDEX idx_configuration_audit_event_event ON configuration_audit_event(event);
