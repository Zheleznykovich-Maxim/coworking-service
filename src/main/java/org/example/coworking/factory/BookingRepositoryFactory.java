package org.example.coworking.factory;

import org.example.coworking.model.Booking;
import org.example.coworking.repository.BookingRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика для создания объектов типа BookingRepository.
 * Реализует интерфейс CoworkingFactory для создания экземпляров репозитория бронирований.
 */
public class BookingRepositoryFactory implements CoworkingFactory<BookingRepository> {

    /**
     * Создает и возвращает новый экземпляр BookingRepository с пустым начальным состоянием.
     *
     * @return новый экземпляр BookingRepository
     */
    @Override
    public BookingRepository create() {
        Map<Integer, Booking> bookingMap = new HashMap<>();
        return new BookingRepository(bookingMap);
    }
}
