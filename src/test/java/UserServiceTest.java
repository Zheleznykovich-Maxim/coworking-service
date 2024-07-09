import org.example.coworking.model.User;
import org.example.coworking.model.enums.UserRole;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Register User Test")
    void register() {

        User user = new User("user1", "password1", UserRole.USER);

        userService.register(user);

        verify(userRepository, times(1)).registerUser(any(User.class));
    }

    @Test
    @DisplayName("Login Successful Test")
    void loginSuccessful() {

        User user = new User("user1", "password1", UserRole.USER);

        when(userRepository.findUserByUsername("user1")).thenReturn(Optional.of(user));

        boolean result = userService.login(user);

        assertThat(result).isTrue();
        verify(userRepository, times(1)).findUserByUsername("user1");
    }

    @Test
    @DisplayName("Login Failed Test")
    void loginFailed() {
        // Arrange
        String username = "user1";
        String password = "wrongpassword";
        User wrongUser = new User(username, password, UserRole.USER);
        User storedUser = new User("user1", "password1", UserRole.USER);
        when(userRepository.findUserByUsername("user1")).thenReturn(Optional.of(storedUser));

        boolean result = userService.login(wrongUser);

        assertThat(result).isFalse();
        verify(userRepository, times(1)).findUserByUsername("user1");
    }

    @Test
    @DisplayName("Find User By Name Test")
    void findUserByName() {

        User user = new User("user1", "password1", UserRole.USER);
        when(userRepository.findUserByUsername("user1")).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByName("user1");

        assertThat(foundUser).isEqualTo(user);
        verify(userRepository, times(1)).findUserByUsername("user1");
    }
}
