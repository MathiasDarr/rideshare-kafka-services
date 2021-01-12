package org.mddarr.containerstestingservice;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mddarr.containerstestingservice.model.Book;
import org.mddarr.containerstestingservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = { BookRepositoryTCIntegrationTest.Initializer.class})
public class BookRepositoryTCIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

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
    public void save_and_read_book(){
        Book book = new Book();
        book.setTitle("My fancy title");

        bookRepository.save(book);

        List<Book> allBooks = bookRepository.findAll();

        assertThat(allBooks).isNotEmpty();
        assertThat(allBooks).hasSize(1);
        assertThat(allBooks.get(0).getTitle()).isEqualTo("My fancy title");
    }

}
