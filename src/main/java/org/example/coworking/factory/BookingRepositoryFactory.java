package org.example.coworking.factory;

import org.example.coworking.repository.BookingRepository;

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
        return new BookingRepository();
    }
}
