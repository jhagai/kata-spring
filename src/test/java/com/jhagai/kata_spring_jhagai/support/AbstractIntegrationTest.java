package com.jhagai.kata_spring_jhagai.support;

import com.jhagai.kata_spring_jhagai.TestcontainersConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for integration tests that hit the real (Testcontainers) Postgres
 * database through the whole Spring context.
 * <p>
 * Deliberately NOT annotated {@code @Transactional}: wrapping each test in a single
 * rolled-back transaction would keep one Hibernate session open for the whole test,
 * which would silently make the lazy-loading / N+1 exercises impossible to get wrong
 * - defeating their purpose. Each test is responsible for its own data via the working
 * use cases, and {@link #cleanDatabase()} wipes every table after each test instead.
 * <p>
 * {@code @ActiveProfiles("test")} activates the "test" profile, which excludes
 * {@code DataSeeder} (it only runs on {@code @Profile("!test")}).
 */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE comments, ticket_tag, tags, tickets, sprints, users RESTART IDENTITY CASCADE");
    }
}
