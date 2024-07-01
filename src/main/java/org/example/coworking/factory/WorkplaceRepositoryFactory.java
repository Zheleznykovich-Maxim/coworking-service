package org.example.coworking.factory;

import org.example.coworking.repository.WorkplaceRepository;

/**
 * Фабрика для создания экземпляров {@link WorkplaceRepository}.
 */
public class WorkplaceRepositoryFactory implements CoworkingFactory<WorkplaceRepository> {

    /**
     * Создает и возвращает новый экземпляр {@link WorkplaceRepository}.
     *
     * @return новый экземпляр {@link WorkplaceRepository}.
     */
    @Override
    public WorkplaceRepository create() {
        return new WorkplaceRepository();
    }
}
