package org.mddarr.users.testing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class UsersTestingApplicationTests {

  @Container
  public static PostgreSQLContainer container = new PostgreSQLContainer()
    .withUsername("postgres")
    .withPassword("postgres")
    .withDatabaseName("postgresdb");

  @Autowired
  private UserRepository userRepository;

  // requires Spring Boot >= 2.2.6
  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void contextLoads() {

    User user = new User();
    user.setName("Testcontainers");

    userRepository.save(user);

    System.out.println("Context loads!");
  }

}
