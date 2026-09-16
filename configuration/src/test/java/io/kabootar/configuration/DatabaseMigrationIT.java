package io.kabootar.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
class DatabaseMigrationIT {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("kabootar_db")
                    .withUsername("postgres")
                    .withPassword("postgres");

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    void freshDatabase_shouldContainAllTables() {
        // TODO: run Flyway migration

        assertThat(tableExists("region")).isTrue();
        assertThat(tableExists("services")).isTrue();
        assertThat(tableExists("service_instance")).isTrue();
    }

    @Test
    void seedData_shouldContainExpectedRecords() {
        // TODO: run Flyway migration

        Integer regions = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM region",
                Integer.class
        );

        Integer services = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM services",
                Integer.class
        );

        Integer instances = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM service_instance",
                Integer.class
        );

        assertThat(regions).isEqualTo(3);
        assertThat(services).isEqualTo(1);
        assertThat(instances).isEqualTo(3);
    }

    @Test
    void applicationRestart_shouldNotCreateDuplicates() {
        // TODO: run Flyway migration

        int regionsBefore = count("region");
        int servicesBefore = count("services");
        int instancesBefore = count("service_instance");

        // Simulate application restart
        // TODO: run Flyway migration again

        assertThat(count("region")).isEqualTo(regionsBefore);
        assertThat(count("services")).isEqualTo(servicesBefore);
        assertThat(count("service_instance")).isEqualTo(instancesBefore);
    }

    @Test
    void invalidRegion_shouldFail() {
        // TODO: run Flyway migration

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO service_instance
                        (id, `key`, service_id, region_id)
                    VALUES
                        (gen_random_uuid(), 'invalid-region-instance',
                         (SELECT id FROM services WHERE `key` = 'demo-service'),
                         'non-existing-region')
                    """)
        ).isInstanceOf(Exception.class);
    }

    @Test
    void invalidService_shouldFail() {
        // TODO: run Flyway migration

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO service_instance
                        (id, `key`, service_id, region_id)
                    VALUES
                        (gen_random_uuid(), 'invalid-service-instance',
                         'non-existing-service',
                         (SELECT id FROM region WHERE `key` = 'ap-south-mumbai'))
                    """)
        ).isInstanceOf(Exception.class);
    }

    @Test
    void duplicateRegionKey_shouldFail() {
        // TODO: run Flyway migration

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO region (id, `key`)
                    VALUES (gen_random_uuid(), 'ap-south-mumbai')
                    """)
        ).isInstanceOf(Exception.class);
    }

    private int count(String table) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + table,
                Integer.class
        );
    }

    private boolean tableExists(String tableName) {
        Integer count = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_schema = 'public'
                  AND table_name = ?
                """,
                Integer.class,
                tableName
        );

        return count != null && count > 0;
    }
}
