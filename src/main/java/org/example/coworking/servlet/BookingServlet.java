package org.example.coworking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.dto.request.BookingRequestDto;
import org.example.coworking.dto.response.BookingResponseDto;
import org.example.coworking.factory.BookingServiceFactory;
import org.example.coworking.factory.CoworkingSpaceServiceFactory;
import org.example.coworking.factory.UserServiceFactory;
import org.example.coworking.mapper.BookingMapper;
import org.example.coworking.mapper.BookingMapperImpl;
import org.example.coworking.model.Booking;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@Loggable
@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final CoworkingSpaceService coworkingSpaceService;

    public BookingServlet() throws IOException {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.bookingMapper = new BookingMapperImpl();
        this.bookingService = new BookingServiceFactory().create();
        this.userService = new UserServiceFactory().create();
        this.coworkingSpaceService = new CoworkingSpaceServiceFactory().create();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<Booking> bookings = bookingService.getAllBookings();
            Collection<BookingResponseDto> bookingResponseDtos = bookingMapper.bookingsToBookingResponseDtos(bookings);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), bookingResponseDtos);
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Booking booking = bookingService.findBookingById(id);
                BookingResponseDto bookingResponseDto = bookingMapper.bookingToBookingResponseDto(booking);
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getOutputStream(), bookingResponseDto);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный путь запроса!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BufferedReader reader = req.getReader();
            BookingRequestDto bookingRequestDto = objectMapper.readValue(reader, BookingRequestDto.class);
            Booking booking = bookingMapper.bookingRequestDtotoBooking(bookingRequestDto);
            userService.findUserById(booking.getUserId());
            switch (booking.getResourceType()) {
                case CONFERENCEHALL -> coworkingSpaceService.findConferenceHallById(booking.getResourceId());
                case WORKPLACE -> coworkingSpaceService.findWorkplaceById(booking.getResourceId());
            }
            bookingService.addBooking(booking);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            BufferedReader reader = req.getReader();
            BookingRequestDto bookingRequestDto = objectMapper.readValue(reader, BookingRequestDto.class);
            Booking booking = bookingMapper.bookingRequestDtotoBooking(bookingRequestDto);
            booking.setId(id);
            BookingResponseDto bookingResponseDto = bookingMapper.bookingToBookingResponseDto(booking);
            userService.findUserById(id);
            switch (booking.getResourceType()) {
                case WORKPLACE -> coworkingSpaceService.findConferenceHallById(booking.getResourceId());
                case CONFERENCEHALL -> coworkingSpaceService.findWorkplaceById(booking.getResourceId());
            }
            bookingService.updateBooking(booking);
            bookingService.findBookingById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), bookingResponseDto);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            bookingService.findBookingById(id);
            bookingService.deleteBooking(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        }
    }
}
