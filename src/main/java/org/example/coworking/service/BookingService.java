package org.example.coworking.service;

import lombok.AllArgsConstructor;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.Booking;
import org.example.coworking.model.User;
import org.example.coworking.repository.BookingRepository;
import java.time.LocalDate;
import java.util.Collection;

@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public void addBooking(Booking booking) {
            bookingRepository.addBooking(booking);
    }

    public void deleteBooking(int bookingId) {
        bookingRepository.removeBookingById(bookingId);
    }

    public Collection<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    public Collection<Booking> filterBookingsByDate(LocalDate date) {
        return bookingRepository.filterBookingsByDate(date);
    }

    // Метод для фильтрации бронирований по пользователю
    public Collection<Booking> filterBookingsByUser(User user) {
        return bookingRepository.filterBookingByUser(user);
    }

    // Метод для фильтрации бронирований по ресурсу
    public Collection<Booking> filterBookingsByResource(ResourceType resourceType) {
        return bookingRepository.filterBookingByResource(resourceType);
    }

    public Booking findBookingById(int id) {
        return bookingRepository.findBookingById(id);
    }

}
