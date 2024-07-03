package org.example.coworking.factory;

import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;

public class BookingServiceFactory implements CoworkingFactory<BookingService>{
    @Override
    public BookingService create() {
        BookingRepository bookingRepository = new BookingRepository();
        return new BookingService(bookingRepository);
    }
}
