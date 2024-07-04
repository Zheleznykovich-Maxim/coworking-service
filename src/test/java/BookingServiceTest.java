import org.assertj.core.api.Assertions;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("Tests for BookingService")
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test adding a booking")
    public void testAddBooking() throws SQLException, IOException {
        Booking booking = new Booking();
        doNothing().when(bookingRepository).addBooking(ArgumentMatchers.any(Booking.class));

        bookingService.addBooking(booking);

        verify(bookingRepository, times(1)).addBooking(booking);
    }

    @Test
    @DisplayName("Test deleting a booking")
    public void testDeleteBooking() {
        int bookingId = 1;
        doNothing().when(bookingRepository).removeBookingById(bookingId);

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).removeBookingById(bookingId);
    }

    @Test
    @DisplayName("Test getting all bookings")
    public void testGetAllBookings() throws IOException {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Collection<Booking> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.getAllBookings()).thenReturn(bookings);

        Collection<Booking> result = bookingService.getAllBookings();

        Assertions.assertThat(result).containsExactlyElementsOf(bookings);
    }

    @Test
    @DisplayName("Test updating a booking")
    public void testUpdateBooking() throws IOException, SQLException {
        Booking booking = new Booking();
        doNothing().when(bookingRepository).updateBooking(ArgumentMatchers.any(Booking.class));

        bookingService.updateBooking(booking);

        verify(bookingRepository, times(1)).updateBooking(booking);
    }

    @Test
    @DisplayName("Test filtering bookings by date")
    public void testFilterBookingsByDate() {
        LocalDate date = LocalDate.now();
        when(bookingRepository.filterBookingsByDate(date)).thenReturn(Collections.emptyList());

        Collection<Booking> result = bookingService.filterBookingsByDate(date);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test filtering bookings by user")
    public void testFilterBookingsByUser() {
        User user = new User();
        when(bookingRepository.filterBookingsByUser(user)).thenReturn(Collections.emptyList());

        Collection<Booking> result = bookingService.filterBookingsByUser(user);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test filtering bookings by resource")
    public void testFilterBookingsByResource() {
        ResourceType resourceType = ResourceType.CONFERENCEHALL;
        when(bookingRepository.filterBookingsByResource(resourceType)).thenReturn(Collections.emptyList());

        Collection<Booking> result = bookingService.filterBookingsByResource(resourceType);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test finding a booking by ID")
    public void testFindBookingById() {
        int id = 1;
        Booking booking = new Booking();
        when(bookingRepository.findBookingById(id)).thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.findBookingById(id);

        Assertions.assertThat(result.get()).isEqualTo(booking);
    }
}
