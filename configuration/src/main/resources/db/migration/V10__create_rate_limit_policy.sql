CREATE TABLE rate_limit_policy (
    id CHAR(36) NOT NULL,
    revision_id CHAR(36) NOT NULL,
    requests_per_window BIGINT NOT NULL,
    burst_capacity BIGINT NOT NULL DEFAULT 0,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_rate_limit_policy PRIMARY KEY (id),
    CONSTRAINT uk_rate_limit_policy_revision_id UNIQUE (revision_id, id),
    CONSTRAINT ck_rate_limit_requests CHECK (requests_per_window > 0),
    CONSTRAINT ck_rate_limit_burst CHECK (burst_capacity >= 0),
    CONSTRAINT fk_rate_limit_policy_revision FOREIGN KEY (revision_id)
        REFERENCES configuration_revision(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX idx_rate_limit_policy_enabled ON rate_limit_policy(revision_id, enabled);
