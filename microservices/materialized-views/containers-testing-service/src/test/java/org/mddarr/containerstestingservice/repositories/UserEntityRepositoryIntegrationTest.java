package org.mddarr.containerstestingservice.repositories;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mddarr.containerstestingservice.models.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = { UserEntityRepositoryIntegrationTest.Initializer.class})
public class UserEntityRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11-alpine")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create-drop"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void test_save_user(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserid(UUID.randomUUID().toString());
        userEntity.setFirst_name("Charles");
        userEntity.setLast_name("Goodwin");
        userEntity.setEmail("cgoodwin@gmail.com");

        userRepository.save(userEntity);

        List<UserEntity> allBooks = userRepository.findAll();

        assertThat(allBooks).isNotEmpty();
        assertThat(allBooks).hasSize(1);
        assertThat(allBooks.get(0).getFirst_name()).isEqualTo("Charles");
    }



}
