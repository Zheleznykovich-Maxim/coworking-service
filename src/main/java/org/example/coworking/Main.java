package org.example.coworking;

import org.example.coworking.in.UserConsole;
import org.example.coworking.factory.BookingServiceFactory;
import org.example.coworking.factory.CoworkingSpaceServiceFactory;
import org.example.coworking.factory.CoworkingFactory;
import org.example.coworking.factory.UserConsoleFactory;
import org.example.coworking.factory.UserServiceFactory;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;

public class Main {
    public static void main(String[] args) {
        CoworkingFactory<UserService> userServiceFactory = new UserServiceFactory();
        CoworkingFactory<CoworkingSpaceService> coworkingSpaceServiceFactory = new CoworkingSpaceServiceFactory();
        CoworkingFactory<BookingService> bookingServiceFactory = new BookingServiceFactory();
        CoworkingFactory<UserConsole> userConsoleFactory = new UserConsoleFactory(userServiceFactory, coworkingSpaceServiceFactory, bookingServiceFactory);

        UserConsole userConsole = userConsoleFactory.create();
        userConsole.runStartCommands();
    }
}