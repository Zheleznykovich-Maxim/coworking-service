import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookingService Tests")
public class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private Map<Integer, Booking> bookingMap;

    @BeforeEach
    public void setUp() {
        bookingMap = new HashMap<>();
        bookingRepository = new BookingRepository(bookingMap);
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    @DisplayName("Test creating a booking")
    public void testCreateBooking() throws SQLException, IOException {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);
        int resourceId = 1;
        String resourceName = "Workplace 1";
        ResourceType resourceType = ResourceType.WORKPLACE;

        // Act
        Booking booking = new Booking(null, resourceId, resourceName, startTime, endTime, resourceType, true);
        bookingService.addBooking(booking);

        // Assert
        assertThat(bookingMap).hasSize(1);
        Booking createdBooking = bookingMap.get(booking.getId());
        assertThat(createdBooking.getId()).isEqualTo(booking.getId());
        assertThat(createdBooking.getResourceId()).isEqualTo(resourceId);
        assertThat(createdBooking.getResourceName()).isEqualTo(resourceName);
        assertThat(createdBooking.getStartTime()).isEqualTo(startTime);
        assertThat(createdBooking.getEndTime()).isEqualTo(endTime);
        assertThat(createdBooking.getResourceType()).isEqualTo(resourceType);
    }

    @Test
    @DisplayName("Test deleting a booking")
    public void testDeleteBooking() {
        // Arrange
        Booking booking1 = new Booking(null, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act
        bookingService.deleteBooking(booking1.getId());

        // Assert
        assertThat(bookingMap).hasSize(1);
        assertThat(bookingMap.get(booking2.getId()).getId()).isEqualTo(booking2.getId());
    }

    @Test
    @DisplayName("Test viewing all bookings")
    public void testViewAllBookings() throws IOException {
        // Arrange
        Booking booking1 = new Booking(null, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act (print output using System.out)
        bookingService.getAllBookings();

        // Assert (output verification can be added manually)
    }

    @Test
    @DisplayName("Test filtering bookings by date")
    public void testFilterBookingsByDate() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newStartTime1 = LocalDateTime.of(2024, 6, 24, 9, 0);
        LocalDateTime newEndTime2 = LocalDateTime.of(2024, 6, 25, 11, 0);
        LocalDateTime newStartTime3 = LocalDateTime.of(2024, 6, 27, 12, 0);
        Booking booking1 = new Booking(null, 1, "Workplace 1", newStartTime1, newEndTime2, ResourceType.WORKPLACE, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", newStartTime3, newEndTime2, ResourceType.WORKPLACE, true);
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act
        Collection<Booking> filteredMap = bookingService.filterBookingsByDate(newStartTime3.toLocalDate());
        Booking firstBooking = filteredMap.stream().findFirst().orElse(null);

        // Assert
        assertThat(filteredMap).hasSize(1);
        assertThat(firstBooking.getId()).isEqualTo(booking2.getId());
    }

    @Test
    @DisplayName("Test filtering bookings by user")
    public void testFilterBookingsByUser() {
        // Arrange
        User user1 = new User("John", "password", null);
        User user2 = new User("Jane", "password", null);
        Booking booking1 = new Booking(null, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        booking1.setUserId(user1.getId());
        booking2.setUserId(user2.getId());
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act
        Collection<Booking> filteredMap = bookingService.filterBookingsByUser(user1);
        Booking firstBooking = filteredMap.stream().findFirst().orElse(null);

        // Assert
        assertThat(filteredMap).hasSize(1);
        assertThat(firstBooking.getId()).isEqualTo(booking1.getId());
    }

    @Test
    @DisplayName("Test filtering bookings by resource")
    public void testFilterBookingsByResource() {
        // Arrange
        ResourceType resourceType = ResourceType.WORKPLACE;
        Booking booking1 = new Booking(null, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), resourceType, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.CONFERENCEHALL, true);
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act
        Collection<Booking> filteredMap = bookingService.filterBookingsByResource(resourceType);
        Booking firstBooking = filteredMap.stream().findFirst().orElse(null);

        // Assert
        assertThat(filteredMap).hasSize(1);
        assertThat(firstBooking.getId()).isEqualTo(booking1.getId());
    }

    @Test
    @DisplayName("Test finding a booking by ID")
    public void testFindBookingById() {
        // Arrange
        Booking booking1 = new Booking(null, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        Booking booking2 = new Booking(null, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE, true);
        bookingMap.put(booking1.getId(), booking1);
        bookingMap.put(booking2.getId(), booking2);

        // Act
        Booking foundBooking = bookingService.findBookingById(booking1.getId());

        // Assert
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getId()).isEqualTo(booking1.getId());
    }
}
