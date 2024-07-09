package org.example.coworking.factory;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.CoworkingSpaceService;
import java.io.IOException;

public class CoworkingSpaceServiceFactory implements CoworkingFactory<CoworkingSpaceService> {
    @Override
    public CoworkingSpaceService create() throws IOException {
        ConferenceHallRepository conferenceHallRepository = new ConferenceHallRepository(DatabaseConfig.getDatabaseConnection());
        WorkplaceRepository workplaceRepository = new WorkplaceRepository(DatabaseConfig.getDatabaseConnection());
        return new CoworkingSpaceService(conferenceHallRepository, workplaceRepository);
    }
}
