package booking;

import org.example.coworking.controller.BookingController;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.enums.ResourceType;
import org.example.coworking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for BookingController")
public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    @DisplayName("Test get all bookings")
    public void testGetBookings() throws Exception {

        BookingResponseDto bookingResponseDto = new BookingResponseDto(1, 0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);
        Collection<BookingResponseDto> allBookings = Collections.singletonList(bookingResponseDto);

        when(bookingService.getBookings()).thenReturn(allBookings);

        mockMvc.perform(get("/coworking/booking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test get booking by id")
    public void testGetBookingById() throws Exception {

        BookingResponseDto bookingResponseDto = new BookingResponseDto(1, 0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);

        when(bookingService.findBookingById(1)).thenReturn(bookingResponseDto);

        mockMvc.perform(get("/coworking/booking/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test get bookings by date")
    public void testGetBookingsByDate() throws Exception {

        BookingResponseDto bookingResponseDto = new BookingResponseDto(1, 0, 101,
                LocalDateTime.of(2024, 6, 24, 10, 0),
                LocalDateTime.of(2024, 6, 24, 12, 0),
                ResourceType.WORKPLACE,
                true);

        Collection<BookingResponseDto> bookings = Arrays.asList(bookingResponseDto);

        when(bookingService.filterBookingsByDate(LocalDate.of(2022, 1, 1))).thenReturn(bookings);

        mockMvc.perform(get("/coworking/booking/filter/by-date")
                        .param("date", "2022-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }
}
