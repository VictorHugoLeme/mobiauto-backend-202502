package dev.victorhleme.mobiauto.utils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {


    private static final String POSTGRES_IMAGE = "postgres:latest";
    private static final String POSTGRES_DB = "mobiauto-test";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "postgrespass";

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
        .withDatabaseName(POSTGRES_DB)
        .withUsername(POSTGRES_USER)
        .withPassword(POSTGRES_PASSWORD)
        .withExposedPorts(5432);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }


}
