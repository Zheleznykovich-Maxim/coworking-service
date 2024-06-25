import org.example.coworking.model.enums.UserRole;
import org.example.coworking.model.User;
import org.example.coworking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;

        // Act
        boolean registered = userService.register(username, password, userRole);

        // Assert
        assertTrue(registered);
    }

    @Test
    public void testRegisterDuplicateUser() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        boolean registeredAgain = userService.register(username, "anotherPassword", UserRole.ADMIN);

        // Assert
        assertFalse(registeredAgain);
    }

    @Test
    public void testLoginValidUser() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        boolean loggedIn = userService.login(username, password);

        // Assert
        assertTrue(loggedIn);
    }

    @Test
    public void testLoginInvalidPassword() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        boolean loggedIn = userService.login(username, "wrongPassword");

        // Assert
        assertFalse(loggedIn);
    }

    @Test
    public void testLoginInvalidUsername() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        boolean loggedIn = userService.login("unknownUser", password);

        // Assert
        assertFalse(loggedIn);
    }

    @Test
    public void testFindUserByName() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        User foundUser = userService.findUserByName(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindUserByNameNotFound() {
        // Arrange
        String username = "testuser";
        String password = "password";
        UserRole userRole = UserRole.USER;
        userService.register(username, password, userRole);

        // Act
        User foundUser = userService.findUserByName("unknownUser");

        // Assert
        assertNull(foundUser);
    }
}
