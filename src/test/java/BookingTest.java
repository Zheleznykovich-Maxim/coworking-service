import org.example.coworking.enums.ResourceType;
import org.example.coworking.models.Booking;
import org.example.coworking.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BookingTest {

    private Booking booking;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = mock(User.class);
        booking = new Booking(1, 101, "Meeting Room A",
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE);
    }

    @Test
    public void testConstructorAndGetters() {
        assertThat(booking.getId()).isEqualTo(1);
        assertThat(booking.getResourceId()).isEqualTo(101);
        assertThat(booking.getResourceName()).isEqualTo("Meeting Room A");
        assertThat(booking.getStartTime()).isEqualTo(LocalDateTime.of(2024, 6, 24, 10, 0));
        assertThat(booking.getEndTime()).isEqualTo(LocalDateTime.of(2024, 6, 24, 12, 0));
        assertThat(booking.getResourceType()).isEqualTo(ResourceType.WORKPLACE);
        assertThat(booking.isAvailable()).isTrue();
    }

    @Test
    public void testSetters() {
        booking.setId(2);
        booking.setResourceId(102);
        booking.setResourceName("Conference Room B");
        LocalDateTime newStartTime = LocalDateTime.of(2024, 6, 25, 9, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2024, 6, 25, 11, 0);
        booking.setStartTime(newStartTime);
        booking.setEndTime(newEndTime);
        booking.setResourceType(ResourceType.CONFERENCEHALL);
        booking.setAvailable(false);

        assertThat(booking.getId()).isEqualTo(2);
        assertThat(booking.getResourceId()).isEqualTo(102);
        assertThat(booking.getResourceName()).isEqualTo("Conference Room B");
        assertThat(booking.getStartTime()).isEqualTo(newStartTime);
        assertThat(booking.getEndTime()).isEqualTo(newEndTime);
        assertThat(booking.getResourceType()).isEqualTo(ResourceType.CONFERENCEHALL);
        assertThat(booking.isAvailable()).isFalse();
    }

    @Test
    public void testSetUser() {
        booking.setUser(mockUser);
        assertThat(booking.getUser()).isEqualTo(mockUser);
    }

    @Test
    public void testToString() {
        String expectedString = "Booking{id=1, user=null, resourceId=101, resourceName='Meeting Room A', " +
                "startTime=2024-06-24T10:00, endTime=2024-06-24T12:00, resourceType=WORKPLACE, isAvailable=true}";
        assertThat(booking.toString()).isEqualTo(expectedString);
    }

    @Test
    public void testIsUserNull() {
        assertThat(booking.isUserNull()).isTrue();
        booking.setUser(mockUser);
        assertThat(booking.isUserNull()).isFalse();
    }
}
