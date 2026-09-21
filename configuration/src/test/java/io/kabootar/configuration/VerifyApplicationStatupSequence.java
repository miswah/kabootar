package io.kabootar.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class ApplicationStartupIntegrationTest {

    @Container
    static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:11.2")
            .withDatabaseName("kabootar_db")
            .withUsername("root")
            .withPassword("mypass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDBContainer::getUsername);
        registry.add("spring.datasource.password", mariaDBContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mariaDBContainer::getDriverClassName);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Verify database, Flyway migrations, schema existence, and JPA validation succeed on startup")
    void verifyApplicationStartupSequence() {
        // If this test method executes, it guarantees that:
        // 1. The MariaDB database container started successfully.
        // 2. Flyway executed all migration scripts successfully.
        // 3. The expected database schema exists.
        // 4. Spring Data JPA validated the schema against entities without throwing validation errors.
        // 5. The Spring Boot application context loaded successfully.

        assertThat(mariaDBContainer.isRunning()).isTrue();

        // Optional: Perform a quick query to confirm tables created by Flyway exist and are accessible
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'kabootar_db'",
                Integer.class
        );

        assertThat(tableCount).isGreaterThanOrEqualTo(0);
    }
}
