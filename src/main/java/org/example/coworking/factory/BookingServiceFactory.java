package org.example.coworking.factory;

import org.example.coworking.model.Booking;
import org.example.coworking.service.BookingService;

import java.util.ArrayList;

public class BookingServiceFactory implements CoworkingFactory<BookingService> {
    @Override
    public BookingService create() {
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        return new BookingService(bookingArrayList);
    }
}
