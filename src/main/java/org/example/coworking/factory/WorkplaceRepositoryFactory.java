package org.example.coworking.factory;

import org.example.coworking.model.Workplace;
import org.example.coworking.repository.WorkplaceRepository;

import java.util.HashMap;
import java.util.Map;

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
        Map<Integer, Workplace> workplaceRepositoryMap = new HashMap<>();
        return new WorkplaceRepository(workplaceRepositoryMap);
    }
}
