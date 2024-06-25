package org.example.coworking.factory;

import org.example.coworking.in.UserConsole;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;

public class UserConsoleFactory implements CoworkingFactory<UserConsole> {
    private final CoworkingFactory<UserService> userServiceFactory;
    private final CoworkingFactory<CoworkingSpaceService> coworkingSpaceServiceFactory;
    private final CoworkingFactory<BookingService> bookingServiceFactory;

    public UserConsoleFactory(CoworkingFactory<UserService> userServiceFactory, CoworkingFactory<CoworkingSpaceService> coworkingSpaceServiceFactory, CoworkingFactory<BookingService> bookingServiceFactory) {
        this.userServiceFactory = userServiceFactory;
        this.coworkingSpaceServiceFactory = coworkingSpaceServiceFactory;
        this.bookingServiceFactory = bookingServiceFactory;
    }

    @Override
    public UserConsole create() {
        UserService userService = userServiceFactory.create();
        CoworkingSpaceService coworkingSpaceService = coworkingSpaceServiceFactory.create();
        BookingService bookingService = bookingServiceFactory.create();
        return new UserConsole(userService, coworkingSpaceService, bookingService);
    }
}