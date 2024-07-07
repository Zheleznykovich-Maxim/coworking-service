import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Tests for Booking class")
public class BookingTest {

    private Booking booking;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = mock(User.class);
        booking = new Booking(0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);
    }

    @Test
    @DisplayName("Test constructor and getters")
    public void testConstructorAndGetters() {
        assertThat(booking.getUserId()).isEqualTo(0);
        assertThat(booking.getResourceId()).isEqualTo(101);
        assertThat(booking.getStartTime()).isEqualTo(LocalDateTime.of(2024, 6, 24, 10, 0));
        assertThat(booking.getEndTime()).isEqualTo(LocalDateTime.of(2024, 6, 24, 12, 0));
        assertThat(booking.getResourceType()).isEqualTo(ResourceType.WORKPLACE);
        assertThat(booking.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Test setters")
    public void testSetters() {
        booking.setId(2);
        booking.setResourceId(102);
        LocalDateTime newStartTime = LocalDateTime.of(2024, 6, 25, 9, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2024, 6, 25, 11, 0);
        booking.setStartTime(newStartTime);
        booking.setEndTime(newEndTime);
        booking.setResourceType(ResourceType.CONFERENCEHALL);
        booking.setAvailable(false);

        assertThat(booking.getId()).isEqualTo(2);
        assertThat(booking.getResourceId()).isEqualTo(102);
        assertThat(booking.getStartTime()).isEqualTo(newStartTime);
        assertThat(booking.getEndTime()).isEqualTo(newEndTime);
        assertThat(booking.getResourceType()).isEqualTo(ResourceType.CONFERENCEHALL);
        assertThat(booking.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("Test setUser method")
    public void testSetUser() {
        booking.setUserId(mockUser.getId());
        assertThat(booking.getUserId() == mockUser.getId());
    }

    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        String expectedString = "Booking(id=0, userId=0, resourceId=101, " +
                "startTime=2024-06-24T10:00, endTime=2024-06-24T12:00, resourceType=WORKPLACE, isAvailable=true)";
        assertThat(booking.toString()).isEqualTo(expectedString);
    }

}
