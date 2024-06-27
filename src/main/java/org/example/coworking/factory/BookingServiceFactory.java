package org.example.coworking.factory;

import lombok.RequiredArgsConstructor;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.service.BookingService;

/**
 * Фабрика для создания объектов типа BookingService.
 * Реализует интерфейс CoworkingFactory для создания экземпляров сервиса бронирования.
 */
@RequiredArgsConstructor
public class BookingServiceFactory implements CoworkingFactory<BookingService> {
    private final CoworkingFactory<BookingRepository> bookingRepositoryFactory;

    /**
     * Создает и возвращает новый экземпляр BookingService, используя фабрику bookingRepositoryFactory
     * для создания необходимого BookingRepository.
     *
     * @return новый экземпляр BookingService
     */
    @Override
    public BookingService create() {
        return new BookingService(bookingRepositoryFactory.create());
    }
}
