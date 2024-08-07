package org.example.coworking.controller;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.enums.ResourceType;
import org.example.coworking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<BookingResponseDto>> getBookings() {
        Collection<BookingResponseDto> bookingResponseDtos = this.bookingService.getBookings();
        return ResponseEntity.ok(bookingResponseDtos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable("id") int id) {
        BookingResponseDto bookingResponseDto = bookingService.findBookingById(id);
        return ResponseEntity.ok(bookingResponseDto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponseDto> createWorkplace(@RequestBody BookingRequestDto bookingRequestDto) throws SQLException {
        BookingResponseDto bookingResponseDto = bookingService.addBooking(bookingRequestDto);
        return ResponseEntity.status(201).body(bookingResponseDto);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponseDto> updateWorkplace(@PathVariable("id") int id,
                                                                @RequestBody BookingRequestDto workplaceRequestDto) {
        BookingResponseDto bookingResponseDto = bookingService.updateBooking(id, workplaceRequestDto);
        return ResponseEntity.ok(bookingResponseDto);

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponseDto> deleteWorkplace(@PathVariable("id") int id) {
        BookingResponseDto bookingResponseDto = bookingService.deleteBooking(id);
        return ResponseEntity.ok(bookingResponseDto);
    }

    @GetMapping("/filter/by-date")
    public ResponseEntity<Collection<BookingResponseDto>> getWorkplacesByDate(@RequestParam("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Collection<BookingResponseDto> bookingResponseDtos = bookingService.filterBookingsByDate(date);
        return ResponseEntity.ok(bookingResponseDtos);
    }

    @GetMapping("/filter/by-resource-type")
    public ResponseEntity<Collection<BookingResponseDto>> getWorkplacesByResourceType(@RequestParam("type") ResourceType resourceType) throws SQLException {
        Collection<BookingResponseDto> bookingResponseDtos = bookingService.filterBookingsByResource(resourceType);
        return ResponseEntity.ok(bookingResponseDtos);
    }

    @GetMapping("/filter/by-user")
    public ResponseEntity<Collection<BookingResponseDto>> getWorkplacesByUser(@RequestParam("userId") int userId) {
        Collection<BookingResponseDto> bookingResponseDtos = bookingService.filterBookingsByUser(userId);
        return ResponseEntity.ok(bookingResponseDtos);
    }
}
