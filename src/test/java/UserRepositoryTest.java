import config.DatabaseTestContainer;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.UserRole;
import org.example.coworking.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeAll
    static void init() {
        DatabaseTestContainer.startContainer();
    }

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(DatabaseTestContainer.getDatabaseConnection());
    }

    @Test
    @DisplayName("Test registerUser and findUserByUsername methods")
    void testRegisterAndFindUserByUsername() {
        User user = new User();
        user.setUsername("testuser4");
        user.setPassword("password");
        user.setRole(UserRole.USER);

        if (userRepository.findUserByUsername(user.getUsername()).isEmpty()) {
            userRepository.registerUser(user);
        }

        Optional<User> retrievedUser = userRepository.findUserByUsername(user.getUsername());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo(user.getUsername());
        assertThat(retrievedUser.get().getPassword()).isEqualTo(user.getPassword());
        assertThat(retrievedUser.get().getRole()).isEqualTo(user.getRole());
    }

    @AfterAll
    static void tearDown()  {
        DatabaseTestContainer.stopContainer();
    }
}
