package org.example.coworking.factory;

import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.CoworkingSpaceService;

public class CoworkingSpaceServiceFactory implements CoworkingFactory<CoworkingSpaceService> {
    @Override
    public CoworkingSpaceService create() {
        ConferenceHallRepository conferenceHallRepository = new ConferenceHallRepository();
        WorkplaceRepository workplaceRepository = new WorkplaceRepository();
        return new CoworkingSpaceService(conferenceHallRepository, workplaceRepository);
    }
}
