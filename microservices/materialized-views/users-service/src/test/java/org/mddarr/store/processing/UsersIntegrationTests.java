package org.mddarr.store.processing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class UsersIntegrationTests
{

    // will be started before and stopped after each test method
    @Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
            .withDatabaseName("postgresdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Test
    void contextLoads() {
        Assertions.assertTrue(postgresqlContainer.isRunning());
    }
}