INSERT INTO region (id, `key`)
VALUES
    (UUID(), 'ap-south-mumbai'),
    (UUID(), 'ap-southeast-singapore'),
    (UUID(), 'eu-central-frankfurt');

 INSERT INTO services (id, `key`)
 VALUES
     (UUID(), 'demo-service');


INSERT INTO service_instance (
    id,
    `key`,
    service_id,
    region_id
)
SELECT
    UUID(),
    'demo-mumbai-01',
    s.id,
    r.id
FROM services s
JOIN region r ON r.`key` = 'ap-south-mumbai'
WHERE s.`key` = 'demo-service';

INSERT INTO service_instance (
    id,
    `key`,
    service_id,
    region_id
)
SELECT
    UUID(),
    'demo-singapore-01',
    s.id,
    r.id
FROM services s
JOIN region r ON r.`key` = 'ap-southeast-singapore'
WHERE s.`key` = 'demo-service';

INSERT INTO service_instance (
    id,
    `key`,
    service_id,
    region_id
)
SELECT
    UUID(),
    'demo-frankfurt-01',
    s.id,
    r.id
FROM services s
JOIN region r ON r.`key` = 'eu-central-frankfurt'
WHERE s.`key` = 'demo-service';