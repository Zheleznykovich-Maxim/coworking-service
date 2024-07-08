package org.example.coworking.factory;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.repository.UserActionRepository;

import java.io.IOException;

public class UserActionRepositoryFactory implements CoworkingFactory<UserActionRepository> {
    @Override
    public UserActionRepository create() throws IOException {
        return new UserActionRepository(DatabaseConfig.getDatabaseConnection());
    }
}
