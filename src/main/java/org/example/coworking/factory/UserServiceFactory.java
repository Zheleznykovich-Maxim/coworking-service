package org.example.coworking.factory;

import org.example.coworking.config.DatabaseConfig;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.UserService;
import java.io.IOException;

public class UserServiceFactory implements CoworkingFactory<UserService> {
    @Override
    public UserService create() throws IOException {
        UserRepository userRepository = new UserRepository(DatabaseConfig.getDatabaseConnection());
        return new UserService(userRepository);
    }
}
