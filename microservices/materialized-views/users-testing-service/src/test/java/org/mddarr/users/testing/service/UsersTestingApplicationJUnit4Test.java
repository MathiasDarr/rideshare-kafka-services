package org.mddarr.users.testing.service;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = UsersTestingApplicationJUnit4Test.Initializer.class)
public class UsersTestingApplicationJUnit4Test {

  @ClassRule
  public static PostgreSQLContainer container = new PostgreSQLContainer()
    .withUsername("postgres")
    .withPassword("postgres")
    .withDatabaseName("postgresdb");

  @Autowired
  private UserRepository userRepository;

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues values = TestPropertyValues.of(
        "spring.datasource.url=" + container.getJdbcUrl(),
        "spring.datasource.password=" + container.getPassword(),
        "spring.datasource.username=" + container.getUsername()
      );
      values.applyTo(configurableApplicationContext);
    }
  }

  @Test
  public void contextLoads() {
    User user = new User();
    user.setName("Testcontainers");

    userRepository.save(user);

    System.out.println("Context loads!");
  }

}
