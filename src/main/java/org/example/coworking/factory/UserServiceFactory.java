package org.example.coworking.factory;

import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.UserService;

public class UserServiceFactory implements CoworkingFactory<UserService> {
    @Override
    public UserService create() {
        UserRepository userRepository = new UserRepository();
        return new UserService(userRepository);
    }
}
