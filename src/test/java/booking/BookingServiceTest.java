package booking;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.Booking;
import org.example.coworking.domain.model.enums.ResourceType;
import org.example.coworking.mapper.BookingMapper;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Tests for BookingService")
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks BookingServiceImpl bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test adding a booking")
    public void testAddBooking() throws SQLException {

        BookingRequestDto requestDto = new BookingRequestDto(
                1, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                ResourceType.CONFERENCEHALL, true);

        bookingService.addBooking(requestDto);
        verify(bookingRepository, times(1)).addBooking(bookingMapper.bookingRequestDtotoBooking(requestDto));

    }

    @Test
    @DisplayName("Test deleting a booking")
    public void testDeleteBooking() {

        int bookingId = 1;
        Booking booking = new Booking();
        booking.setId(bookingId);

        BookingResponseDto bookingResponseDto = new BookingResponseDto(1, 0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);

        when(bookingRepository.findBookingById(bookingId)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).removeBookingById(bookingId);
        when(bookingMapper.bookingToBookingResponseDto(booking)).thenReturn(bookingResponseDto);

        BookingResponseDto result = bookingService.deleteBooking(bookingId);

        assertThat(result).isEqualTo(bookingResponseDto);
        verify(bookingRepository, times(1)).findBookingById(bookingId);
        verify(bookingRepository, times(1)).removeBookingById(bookingId);
        verify(bookingMapper, times(1)).bookingToBookingResponseDto(booking);
    }

    @Test
    @DisplayName("Test getting all bookings")
    public void testGetAllBookings() {

        Booking booking1 = new Booking(1, 0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);
        Booking booking2 = new Booking(2, 0, 101,
                    LocalDateTime.of(2024, 6, 24, 10, 0),
                    LocalDateTime.of(2024, 6, 24, 12, 0),
                    ResourceType.WORKPLACE,
                    true);
        Collection<Booking> bookings = Arrays.asList(
                booking1,
                booking2
        );
        Collection<BookingResponseDto> bookingResponseDtos = Arrays.asList(
                bookingMapper.bookingToBookingResponseDto(booking1),
                bookingMapper.bookingToBookingResponseDto(booking2)
        );

        when(bookingRepository.getAllBookings()).thenReturn(bookings);
        when(bookingMapper.bookingsToBookingResponseDtos(bookings)).thenReturn(bookingResponseDtos);

        Collection<BookingResponseDto> result = bookingService.getBookings();

        assertThat(result).isEqualTo(bookingResponseDtos);
        verify(bookingRepository, times(1)).getAllBookings();
        verify(bookingMapper, times(1)).bookingsToBookingResponseDtos(bookings);
    }

    @Test
    @DisplayName("Test filtering bookings by date")
    public void testFilterBookingsByDate() {

        LocalDate date = LocalDate.now();
        when(bookingRepository.filterBookingsByDate(date)).thenReturn(Collections.emptyList());


        Collection<BookingResponseDto> result = bookingService.filterBookingsByDate(date);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test finding a booking by userId")
    public void testFindBookingByUserId() {

        int userId = 1;
        when(bookingRepository.filterBookingsByUser(userId)).thenReturn(Collections.emptyList());

        Collection<BookingResponseDto> result = bookingService.filterBookingsByUser(userId);

        assertThat(result).isNotNull();
    }
}
