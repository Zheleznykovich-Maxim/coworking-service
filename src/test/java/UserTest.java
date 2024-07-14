import org.example.coworking.domain.model.User;
import org.example.coworking.domain.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Class Tests")
public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1, "testUser", "password123", UserRole.ADMIN);
    }

    @Test
    @DisplayName("Test Constructor and Getters")
    public void testConstructorAndGetters() {
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("Test Setters")
    public void testSetters() {
        user.setUsername("newUsername");
        user.setPassword("newPassword456");
        user.setRole(UserRole.ADMIN);

        assertThat(user.getUsername()).isEqualTo("newUsername");
        assertThat(user.getPassword()).isEqualTo("newPassword456");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("Test toString() Method")
    public void testToString() {
        String expectedString = "User{username='testUser', role=ADMIN}";
        assertThat(user.toString()).isEqualTo(expectedString);
    }
}
