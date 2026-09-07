CREATE INDEX idx_region_key
    ON region (`key`);

CREATE INDEX idx_services_key
    ON services (`key`);

CREATE INDEX idx_service_instances_key
    ON service_instance (`key`);

CREATE INDEX idx_service_instances_service_id
    ON service_instance (service_id);

CREATE INDEX idx_service_instances_region_id
    ON service_instance (region_id);