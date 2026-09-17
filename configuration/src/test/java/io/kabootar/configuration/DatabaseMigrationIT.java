package io.kabootar.configuration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
class DatabaseMigrationIT {

    @Container
    static MariaDBContainer<?> mariadb =
            new MariaDBContainer<>("mariadb:10.11")
                    .withDatabaseName("kabootar")
                    .withUsername("root")
                    .withPassword("rootpassword");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                mariadb.getJdbcUrl(),
                mariadb.getUsername(),
                mariadb.getPassword()
        );

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void runFlywayMigration() {
        Flyway.configure()
                .dataSource(
                        mariadb.getJdbcUrl(),
                        mariadb.getUsername(),
                        mariadb.getPassword()
                )
                .load()
                .migrate();
    }

    @Test
    void freshDatabase_shouldContainAllTables() {
        runFlywayMigration();

        assertThat(tableExists("region")).isTrue();
        assertThat(tableExists("services")).isTrue();
        assertThat(tableExists("service_instance")).isTrue();
    }

    @Test
    void seedData_shouldContainExpectedRecords() {
        runFlywayMigration();

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
        runFlywayMigration();

        int regionsBefore = count("region");
        int servicesBefore = count("services");
        int instancesBefore = count("service_instance");

        // Simulate application restart by running Flyway again.
        runFlywayMigration();

        assertThat(count("region")).isEqualTo(regionsBefore);
        assertThat(count("services")).isEqualTo(servicesBefore);
        assertThat(count("service_instance")).isEqualTo(instancesBefore);
    }

    @Test
    void invalidRegion_shouldFail() {
        runFlywayMigration();

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO service_instance
                        (id, `key`, service_id, region_id)
                    VALUES
                        (UUID(), 'invalid-region-instance',
                         (SELECT id FROM services WHERE `key` = 'demo-service'),
                         'non-existing-region')
                    """)
        ).isInstanceOf(Exception.class);
    }

    @Test
    void invalidService_shouldFail() {
        runFlywayMigration();

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO service_instance
                        (id, `key`, service_id, region_id)
                    VALUES
                        (UUID(), 'invalid-service-instance',
                         'non-existing-service',
                         (SELECT id FROM region WHERE `key` = 'ap-south-mumbai'))
                    """)
        ).isInstanceOf(Exception.class);
    }

    @Test
    void duplicateRegionKey_shouldFail() {
        runFlywayMigration();

        assertThatThrownBy(() ->
                jdbcTemplate.update("""
                    INSERT INTO region (id, `key`)
                    VALUES (UUID(), 'ap-south-mumbai')
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
                WHERE table_schema = DATABASE()
                  AND table_name = ?
                """,
                Integer.class,
                tableName
        );

        return count != null && count > 0;
    }
}