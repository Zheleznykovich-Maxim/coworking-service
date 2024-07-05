package org.example.coworking.factory;

import org.example.coworking.in.UserConsole;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;

/**
 * Фабрика для создания экземпляров {@link UserConsole}.
 */
public class UserConsoleFactory implements CoworkingFactory<UserConsole> {
    /**
     * Создает и возвращает новый экземпляр {@link UserConsole}.
     *
     * @return новый экземпляр {@link UserConsole}.
     */
    @Override
    public UserConsole create() {
        BookingService bookingService = new BookingServiceFactory().create();
        CoworkingSpaceService coworkingSpaceService = new CoworkingSpaceServiceFactory().create();
        UserService userService = new UserServiceFactory().create();
        return new UserConsole(userService, coworkingSpaceService, bookingService);
    }
}
