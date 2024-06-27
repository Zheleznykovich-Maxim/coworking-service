package org.example.coworking.factory;

import org.example.coworking.model.User;
import org.example.coworking.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика для создания экземпляров {@link UserRepository}.
 */
public class UserRepositoryFactory implements CoworkingFactory<UserRepository> {

    /**
     * Создает и возвращает новый экземпляр {@link UserRepository}.
     *
     * @return новый экземпляр {@link UserRepository}.
     */
    @Override
    public UserRepository create() {
        Map<String, User> userMap = new HashMap<>();
        return new UserRepository(userMap);
    }
}
