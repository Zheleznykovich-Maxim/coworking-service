package org.example.coworking.factory;

import org.example.coworking.model.Booking;
import org.example.coworking.repository.BookingRepository;

import java.util.HashMap;
import java.util.Map;

public class BookingRepositoryFactory implements CoworkingFactory<BookingRepository> {

    @Override
    public BookingRepository create() {
        Map<Integer, Booking> bookingMap = new HashMap<>();
        return new BookingRepository(bookingMap);
    }
}
