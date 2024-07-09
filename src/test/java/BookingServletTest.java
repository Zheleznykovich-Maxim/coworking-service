import org.example.coworking.dto.request.BookingRequestDto;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.servlet.BookingServlet;
import org.example.coworking.service.BookingService;
import org.example.coworking.model.Booking;
import org.example.coworking.mapper.BookingMapper;
import org.example.coworking.dto.response.BookingResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

public class BookingServletTest {

    @InjectMocks
    private BookingServlet bookingServlet;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoGetWithNoPath() throws Exception {
        when(request.getPathInfo()).thenReturn(null);

        Collection<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getAllBookings()).thenReturn(bookings);

        Collection<BookingResponseDto> bookingResponseDtos = bookingMapper.bookingsToBookingResponseDtos(bookings);
        when(bookingMapper.bookingsToBookingResponseDtos(bookings)).thenReturn(bookingResponseDtos);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        bookingServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithValidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/2");

        Booking booking = new Booking();
        when(bookingService.findBookingById(2)).thenReturn(booking);

        BookingResponseDto bookingResponseDto = bookingMapper.bookingToBookingResponseDto(booking);
        when(bookingMapper.bookingToBookingResponseDto(booking)).thenReturn(bookingResponseDto);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        bookingServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithInvalidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/invalid");

        bookingServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный путь запроса!");
    }

    @Test
    public void testDoPost() throws Exception {
        String jsonInput = "{\"userId\": 1," +
                " \"resourceId\": 2," +
                " \"startTime\": \"2024-06-01T10:00:00\"," +
                " \"endTime\": \"2024-07-01T10:00:00\", " +
                "\"resourceType\": \"WORKPLACE\"," +
                " \"available\": true" +
                "}";
        when(request.getReader()).thenReturn(new DummyReader(jsonInput));

        BookingRequestDto bookingRequestDto = new BookingRequestDto(
                1,
                1,
                LocalDateTime.of(2024, 7, 8, 15, 30),
                LocalDateTime.of(2024, 7, 15, 15, 30),
                ResourceType.WORKPLACE,
                true
                );

        Booking booking = new Booking();
        when(bookingMapper.bookingRequestDtotoBooking(bookingRequestDto)).thenReturn(booking);

        doNothing().when(bookingService).addBooking(booking);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        bookingServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void testDoPutWithValidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/2");

        String jsonInput = "{\"userId\": 1," +
                " \"resourceId\": 2," +
                " \"startTime\": \"2024-06-01T10:00:00\"," +
                " \"endTime\": \"2024-07-01T10:00:00\", " +
                "\"resourceType\": \"WORKPLACE\"," +
                " \"available\": true" +
                "}";
        when(request.getReader()).thenReturn(new DummyReader(jsonInput));

        BookingRequestDto bookingRequestDto = new BookingRequestDto(
                1,
                1,
                LocalDateTime.of(2024, 7, 8, 15, 30),
                LocalDateTime.of(2024, 7, 15, 15, 30),
                ResourceType.WORKPLACE,
                true
        );

        Booking booking = new Booking();
        when(bookingMapper.bookingRequestDtotoBooking(bookingRequestDto)).thenReturn(booking);

        BookingResponseDto bookingResponseDto = bookingMapper.bookingToBookingResponseDto(booking);
        when(bookingMapper.bookingToBookingResponseDto(booking)).thenReturn(bookingResponseDto);

        doNothing().when(bookingService).updateBooking(booking);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        bookingServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    private class ServletOutputStreamWrapper extends jakarta.servlet.ServletOutputStream {
        private ByteArrayOutputStream outputStream;

        public ServletOutputStreamWrapper(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) {
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(jakarta.servlet.WriteListener writeListener) {
        }
    }

    // DummyReader class to mock request reader
    private class DummyReader extends java.io.BufferedReader {
        public DummyReader(String data) {
            super(new java.io.StringReader(data));
        }
    }
}
