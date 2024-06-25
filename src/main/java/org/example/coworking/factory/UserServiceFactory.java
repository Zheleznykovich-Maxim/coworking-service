package org.example.coworking.factory;

import org.example.coworking.service.UserService;

public class UserServiceFactory implements CoworkingFactory<UserService> {
    @Override
    public UserService create() {
        return new UserService();
    }
}
