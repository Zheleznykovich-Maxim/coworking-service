package user;

import config.TestContainerManager;
import org.example.coworking.MainApplication;
import org.example.coworking.domain.model.User;
import org.example.coworking.domain.model.enums.UserRole;
import org.example.coworking.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MainApplication.class)
@Testcontainers
@DisplayName("Tests for UserRepository")
public class UserRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestContainerManager.getPostgresContainer();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void init() {
        postgreSQLContainer.start();
        TestContainerManager.establishConnection();
    }

    @Test
    @DisplayName("Test add workplace")
    void testAddWorkplace() {

        User user = new User(1,"user2", "user2", UserRole.USER);
        userRepository.registerUser(user);

        Optional<User> retrievedUser = userRepository.findUserById(user.getId());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("Test deleting workplace by id")
    void testRemoveWorkplaceById() {

        User user = new User(1, "user3", "user3", UserRole.USER);
        userRepository.registerUser(user);

        userRepository.removeUserById(user.getId());

        Optional<User> retrievedUser = userRepository.findUserById(user.getId());
        assertThat(retrievedUser).isEmpty();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }
}
