package org.example.coworking;

import org.example.coworking.config.LiquibaseConfig;
import org.example.coworking.factory.BookingRepositoryFactory;
import org.example.coworking.factory.ConferenceHallRepositoryFactory;
import org.example.coworking.factory.UserRepositoryFactory;
import org.example.coworking.factory.WorkplaceRepositoryFactory;
import org.example.coworking.in.UserConsole;
import org.example.coworking.factory.BookingServiceFactory;
import org.example.coworking.factory.CoworkingSpaceServiceFactory;
import org.example.coworking.factory.CoworkingFactory;
import org.example.coworking.factory.UserConsoleFactory;
import org.example.coworking.factory.UserServiceFactory;
import org.example.coworking.repository.BookingRepository;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;

public class Main {

    public static void main(String[] args) {
        LiquibaseConfig.runMigrations();

        CoworkingFactory<UserRepository> userRepositoryFactory = new UserRepositoryFactory();
        CoworkingFactory<BookingRepository> bookingRepositoryFactory = new BookingRepositoryFactory();
        CoworkingFactory<WorkplaceRepository> workplaceRepositoryFactory = new WorkplaceRepositoryFactory();
        CoworkingFactory<UserService> userServiceFactory = new UserServiceFactory(userRepositoryFactory);
        CoworkingFactory<UserConsole> userConsoleFactory = getUserConsoleCoworkingFactory(workplaceRepositoryFactory, bookingRepositoryFactory, userServiceFactory);

        UserConsole userConsole = userConsoleFactory.create();
        userConsole.runStartCommands();
    }

    private static CoworkingFactory<UserConsole> getUserConsoleCoworkingFactory(CoworkingFactory<WorkplaceRepository> workplaceRepositoryFactory, CoworkingFactory<BookingRepository> bookingRepositoryFactory, CoworkingFactory<UserService> userServiceFactory) {
        CoworkingFactory<ConferenceHallRepository> conferenceHallRepositoryFactory = new ConferenceHallRepositoryFactory();
        CoworkingFactory<CoworkingSpaceService> coworkingSpaceServiceFactory = new CoworkingSpaceServiceFactory(workplaceRepositoryFactory,
                conferenceHallRepositoryFactory);
        CoworkingFactory<BookingService> bookingServiceFactory = new BookingServiceFactory(bookingRepositoryFactory);
        return new UserConsoleFactory(userServiceFactory, coworkingSpaceServiceFactory, bookingServiceFactory);
    }
}