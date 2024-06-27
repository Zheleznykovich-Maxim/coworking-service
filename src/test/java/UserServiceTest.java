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
        // Arrange
        User user = new User("user1", "password1", UserRole.USER);

        // Act
        userService.register(user);

        // Assert
        verify(userRepository, times(1)).registerUser(any(User.class));
    }

    @Test
    @DisplayName("Login Successful Test")
    void loginSuccessful() {
        // Arrange
        User user = new User("user1", "password1", UserRole.USER);
        when(userRepository.findUserByUsername("user1")).thenReturn(user);

        // Act
        boolean result = userService.login(user);

        // Assert
        assertThat(result).isTrue();
        verify(userRepository, times(1)).findUserByUsername("user1");
    }

    @Test
    @DisplayName("Login Failed Test")
    void loginFailed() {
        // Arrange
        User user = new User("user1", "wrongpassword", UserRole.USER);
        User storedUser = new User("user1", "password1", UserRole.USER);
        when(userRepository.findUserByUsername("user1")).thenReturn(storedUser);

        // Act
        boolean result = userService.login(user);

        // Assert
        assertThat(result).isFalse();
        verify(userRepository, times(1)).findUserByUsername("user1");
    }

    @Test
    @DisplayName("Find User By Name Test")
    void findUserByName() {
        // Arrange
        User user = new User("user1", "password1", UserRole.USER);
        when(userRepository.findUserByUsername("user1")).thenReturn(user);

        // Act
        User foundUser = userService.findUserByName("user1");

        // Assert
        assertThat(foundUser).isEqualTo(user);
        verify(userRepository, times(1)).findUserByUsername("user1");
    }
}
