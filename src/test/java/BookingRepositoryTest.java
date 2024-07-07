import config.DatabaseTestContainer;
import org.example.coworking.model.Booking;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.repository.BookingRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class BookingRepositoryTest {
    private BookingRepository bookingRepository;

    @BeforeAll
    static void init() {
        DatabaseTestContainer.startContainer();
    }

    @BeforeEach
    void setUp() {
        bookingRepository = new BookingRepository(DatabaseTestContainer.getDatabaseConnection());
    }

    @Test
    @DisplayName("Test addBooking method")
    void testAddBooking() throws SQLException {
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setResourceId(1);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(1));
        booking.setResourceType(ResourceType.CONFERENCEHALL);
        booking.setAvailable(true);

        bookingRepository.addBooking(booking);

        Optional<Booking> retrievedBooking = bookingRepository.findBookingById(booking.getId());
        assertThat(retrievedBooking).isPresent();
        assertThat(retrievedBooking.get().getUserId()).isEqualTo(booking.getUserId());
        assertThat(retrievedBooking.get().getResourceId()).isEqualTo(booking.getResourceId());
    }

    @AfterAll
    static void tearDown() {
        DatabaseTestContainer.stopContainer();
    }
}
