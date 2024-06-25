import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingServiceTest {

    private BookingService bookingService;
    private ArrayList<Booking> bookingArrayList;

    @BeforeEach
    public void setUp() {
        bookingArrayList = new ArrayList<>();
        bookingService = new BookingService(bookingArrayList);
    }

    @Test
    public void testCreateBooking() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 24, 12, 0);
        int resourceId = 1;
        String resourceName = "Workplace 1";
        ResourceType resourceType = ResourceType.WORKPLACE;

        // Act
        bookingService.createBooking(resourceId, resourceName, startTime, endTime, resourceType);

        // Assert
        assertThat(bookingArrayList).hasSize(1);
        Booking createdBooking = bookingArrayList.get(0);
        assertThat(createdBooking.getId()).isEqualTo(1);
        assertThat(createdBooking.getResourceId()).isEqualTo(resourceId);
        assertThat(createdBooking.getResourceName()).isEqualTo(resourceName);
        assertThat(createdBooking.getStartTime()).isEqualTo(startTime);
        assertThat(createdBooking.getEndTime()).isEqualTo(endTime);
        assertThat(createdBooking.getResourceType()).isEqualTo(resourceType);
    }

    @Test
    public void testDeleteBooking() {
        // Arrange
        Booking booking1 = new Booking(1, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        Booking booking2 = new Booking(2, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        bookingArrayList.addAll(Arrays.asList(booking1, booking2));

        // Act
        bookingService.deleteBooking(booking1.getId());

        // Assert
        assertThat(bookingArrayList).hasSize(1);
        assertThat(bookingArrayList.get(0).getId()).isEqualTo(booking2.getId());
    }

    @Test
    public void testViewAllBookings() {
        // Arrange
        Booking booking1 = new Booking(1, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        Booking booking2 = new Booking(2, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        bookingArrayList.addAll(Arrays.asList(booking1, booking2));

        // Act (print output using System.out)
        bookingService.viewAllBookings();

        // Assert (output verification can be added manually)
    }

    @Test
    public void testFilterBookingsByDate() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newStartTime1 = LocalDateTime.of(2024, 6, 24, 9, 0);
        LocalDateTime newEndTime2 = LocalDateTime.of(2024, 6, 25, 11, 0);
        LocalDateTime newStartTime3 = LocalDateTime.of(2024, 6, 27, 12, 0);
        Booking booking1 = new Booking(1, 1, "Workplace 1", newStartTime1, newEndTime2, ResourceType.WORKPLACE);
        Booking booking2 = new Booking(2, 2, "Workplace 2", newStartTime3, newEndTime2, ResourceType.WORKPLACE);
        bookingArrayList.add(booking1);
        bookingArrayList.add(booking2);

        // Act
        List<Booking> filteredList = bookingService.filterBookingsByDate(newStartTime3.toLocalDate());

        // Assert
        assertThat(filteredList).hasSize(1);
        assertThat(filteredList.get(0).getId()).isEqualTo(booking2.getId());
    }

    @Test
    public void testFilterBookingsByUser() {
        // Arrange
        User user1 = new User("John", "password", null);
        User user2 = new User("Jane", "password", null);
        Booking booking1 = new Booking(1, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        Booking booking2 = new Booking(2, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        booking1.setUser(user1);
        booking2.setUser(user2);
        bookingArrayList.addAll(Arrays.asList(booking1, booking2));

        // Act
        List<Booking> filteredList = bookingService.filterBookingsByUser(user1);

        // Assert
        assertThat(filteredList).hasSize(1);
        assertThat(filteredList.get(0).getId()).isEqualTo(booking1.getId());
    }

    @Test
    public void testFilterBookingsByResource() {
        // Arrange
        ResourceType resourceType = ResourceType.WORKPLACE;
        Booking booking1 = new Booking(1, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), resourceType);
        Booking booking2 = new Booking(2, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.CONFERENCEHALL);
        bookingArrayList.addAll(Arrays.asList(booking1, booking2));

        // Act
        List<Booking> filteredList = bookingService.filterBookingsByResource(resourceType);

        // Assert
        assertThat(filteredList).hasSize(1);
        assertThat(filteredList.get(0).getId()).isEqualTo(booking1.getId());
    }

    @Test
    public void testFindBookingById() {
        // Arrange
        Booking booking1 = new Booking(1, 1, "Workplace 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        Booking booking2 = new Booking(2, 2, "Workplace 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), ResourceType.WORKPLACE);
        bookingArrayList.addAll(Arrays.asList(booking1, booking2));

        // Act
        Booking foundBooking = bookingService.findBookingById(booking1.getId());

        // Assert
        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getId()).isEqualTo(booking1.getId());
    }
}
