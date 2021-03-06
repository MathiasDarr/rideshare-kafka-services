package org.mddarr.users.testing.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class ReuseContainerTests {

  /**
   * Don't forget to set:
   *
   * testcontainers.reuse.enable=true
   *
   * ... inside your ~/.testcontainers.properties file
   */

  public static PostgreSQLContainer container;

  static {
    container = (PostgreSQLContainer) new PostgreSQLContainer()
      .withUsername("duke")
      .withPassword("password")
      .withDatabaseName("test")
      .withReuse(true);

    container.start();
  }

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
