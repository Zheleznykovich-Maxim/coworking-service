package org.example.coworking.factory;

import org.example.coworking.repository.ConferenceHallRepository;

/**
 * Фабрика для создания объектов типа ConferenceHallRepository.
 * Реализует интерфейс CoworkingFactory для создания экземпляров репозитория конференц-залов.
 */
public class ConferenceHallRepositoryFactory implements CoworkingFactory<ConferenceHallRepository> {

    /**
     * Создает и возвращает новый экземпляр ConferenceHallRepository с пустым начальным маппингом конференц-залов.
     *
     * @return новый экземпляр ConferenceHallRepository
     */
    @Override
    public ConferenceHallRepository create() {
        return new ConferenceHallRepository();
    }
}
