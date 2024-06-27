package org.example.coworking.factory;

import lombok.RequiredArgsConstructor;
import org.example.coworking.in.UserConsole;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;

/**
 * Фабрика для создания экземпляров {@link UserConsole}.
 */
@RequiredArgsConstructor
public class UserConsoleFactory implements CoworkingFactory<UserConsole> {
    private final CoworkingFactory<UserService> userServiceFactory;
    private final CoworkingFactory<CoworkingSpaceService> coworkingSpaceServiceFactory;
    private final CoworkingFactory<BookingService> bookingServiceFactory;

    /**
     * Создает и возвращает новый экземпляр {@link UserConsole}.
     *
     * @return новый экземпляр {@link UserConsole}.
     */
    @Override
    public UserConsole create() {
        UserService userService = userServiceFactory.create();
        CoworkingSpaceService coworkingSpaceService = coworkingSpaceServiceFactory.create();
        BookingService bookingService = bookingServiceFactory.create();
        return new UserConsole(userService, coworkingSpaceService, bookingService);
    }
}
