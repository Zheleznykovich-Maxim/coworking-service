package org.example.coworking.factory;

import lombok.RequiredArgsConstructor;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.UserService;

/**
 * Фабрика для создания экземпляров {@link UserService}.
 */
@RequiredArgsConstructor
public class UserServiceFactory implements CoworkingFactory<UserService> {
    private final CoworkingFactory<UserRepository> userRepositoryFactory;

    /**
     * Создает и возвращает новый экземпляр {@link UserService}.
     *
     * @return новый экземпляр {@link UserService}.
     */
    @Override
    public UserService create() {
        return new UserService(userRepositoryFactory.create());
    }
}
