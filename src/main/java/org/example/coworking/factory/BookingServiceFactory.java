package org.example.coworking.factory;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;
import java.io.IOException;

public class BookingServiceFactory implements CoworkingFactory<BookingService>{
    @Override
    public BookingService create() throws IOException {
        BookingRepository bookingRepository = new BookingRepository(DatabaseConfig.getDatabaseConnection());
        return new BookingService(bookingRepository);
    }
}
