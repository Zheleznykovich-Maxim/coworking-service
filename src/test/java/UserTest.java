import org.example.coworking.enums.UserRole;
import org.example.coworking.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testUser", "password123", UserRole.ADMIN);
    }

    @Test
    public void testConstructorAndGetters() {
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    public void testSetters() {
        user.setUsername("newUsername");
        user.setPassword("newPassword456");
        user.setRole(UserRole.ADMIN);

        assertThat(user.getUsername()).isEqualTo("newUsername");
        assertThat(user.getPassword()).isEqualTo("newPassword456");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    public void testToString() {
        String expectedString = "User{username='testUser', role=ADMIN}";
        assertThat(user.toString()).isEqualTo(expectedString);
    }
}
