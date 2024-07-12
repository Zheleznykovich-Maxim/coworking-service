package org.example.coworking.service.impl;

import org.example.coworking.domain.dto.request.BookingRequestDto;
import org.example.coworking.domain.dto.response.BookingResponseDto;
import org.example.coworking.domain.model.Booking;
import org.example.coworking.exception.EntityNotFoundException;
import org.example.coworking.mapper.BookingMapper;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public Collection<BookingResponseDto> getBookings() {
        Collection<Booking> bookings = bookingRepository.getAllBookings();
        return bookingMapper.bookingsToBookingResponseDtos(bookings);
    }

    @Override
    public BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) {
        Booking booking = bookingMapper.bookingRequestDtotoBooking(bookingRequestDto);
        bookingRepository.addBooking(booking);
        return bookingMapper.bookingToBookingResponseDto(booking);
    }

    @Override
    public BookingResponseDto updateBooking(int id, BookingRequestDto bookingRequestDto) {
        Booking booking = bookingRepository.findBookingById(id)
                .orElseThrow(() -> new EntityNotFoundException("booking", id));
        Booking updatedBooking = bookingMapper.bookingRequestDtotoBooking(bookingRequestDto);
        updatedBooking.setId(booking.getId());
        bookingRepository.updateBooking(updatedBooking);
        return bookingMapper.bookingToBookingResponseDto(booking);
    }

    @Override
    public BookingResponseDto findBookingById(int id) {
        Booking booking = bookingRepository.findBookingById(id)
                .orElseThrow(() -> new EntityNotFoundException("booking", id));
        return bookingMapper.bookingToBookingResponseDto(booking);
    }

    @Override
    public BookingResponseDto deleteBooking(int id) {
        Booking booking = bookingRepository.findBookingById(id)
                .orElseThrow(() -> new EntityNotFoundException("booking", id));
        bookingRepository.removeBookingById(booking.getId());
        return bookingMapper.bookingToBookingResponseDto(booking);
    }
}
