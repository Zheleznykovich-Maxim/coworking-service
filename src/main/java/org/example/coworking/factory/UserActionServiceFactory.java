package org.example.coworking.factory;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.repository.UserActionRepository;
import org.example.coworking.service.UserActionService;

import java.io.IOException;

public class UserActionServiceFactory implements CoworkingFactory<UserActionService> {
    @Override
    public UserActionService create() throws IOException {
        UserActionRepository userActionRepository = new UserActionRepository(DatabaseConfig.getDatabaseConnection());
        return new UserActionService(userActionRepository);
    }
}
