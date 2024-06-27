package org.example.coworking.factory;

import lombok.RequiredArgsConstructor;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.CoworkingSpaceService;

/**
 * Фабрика для создания экземпляров {@link CoworkingSpaceService}.
 */
@RequiredArgsConstructor
public class CoworkingSpaceServiceFactory implements CoworkingFactory<CoworkingSpaceService> {
    private final CoworkingFactory<WorkplaceRepository> workplaceRepositoryFactory;
    private final CoworkingFactory<ConferenceHallRepository> conferenceHallRepositoryFactory;

    /**
     * Создает и возвращает новый экземпляр {@link CoworkingSpaceService}.
     *
     * @return новый экземпляр {@link CoworkingSpaceService}.
     */
    @Override
    public CoworkingSpaceService create() {
        return new CoworkingSpaceService(conferenceHallRepositoryFactory.create(),
                workplaceRepositoryFactory.create());
    }
}
