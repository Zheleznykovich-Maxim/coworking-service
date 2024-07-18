package user;

import config.TestContainerManager;
import org.example.coworking.config.DatabaseConnectionProviderImpl;
import org.example.coworking.domain.model.User;
import org.example.coworking.domain.model.enums.UserRole;
import org.example.coworking.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("Tests for UserRepository")
public class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository(
            new DatabaseConnectionProviderImpl(
                    postgreSQLContainer.getJdbcUrl(),
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword())
    );

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestContainerManager.getPostgresContainer();

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
