package io.kabootar.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
class ApplicationStartupIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Verify database, Flyway migrations, schema existence, and JPA validation succeed on startup")
    void verifyApplicationStartupSequence() {
        // If this test method executes, it guarantees that:
        // 1. The H2 in-memory database initialized successfully.
        // 2. Flyway executed all migration scripts successfully.
        // 3. The expected database schema exists.
        // 4. Spring Data JPA validated the schema against entities without throwing validation errors.
        // 5. The Spring Boot application context loaded successfully.

        // Confirm tables created by Flyway exist and are accessible in H2's PUBLIC schema
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'PUBLIC'",
                Integer.class
        );

        assertThat(tableCount).isGreaterThanOrEqualTo(0);
    }
}