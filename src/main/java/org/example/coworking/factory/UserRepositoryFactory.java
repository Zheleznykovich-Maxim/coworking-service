package org.example.coworking.factory;

import org.example.coworking.repository.UserRepository;

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
        return new UserRepository();
    }
}
