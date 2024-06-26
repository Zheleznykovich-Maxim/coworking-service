package org.example.coworking.factory;

import lombok.RequiredArgsConstructor;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;

@RequiredArgsConstructor
public class BookingServiceFactory implements CoworkingFactory<BookingService> {
    private final CoworkingFactory<BookingRepository> bookingRepositoryFactory;

    @Override
    public BookingService create() {
        return new BookingService(bookingRepositoryFactory.create());
    }
}
