package org.example.coworking.in;

import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.model.enums.UserRole;
import org.example.coworking.model.Booking;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.model.User;
import org.example.coworking.model.Workplace;
import org.example.coworking.out.ConsoleUI;
import org.example.coworking.service.BookingService;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.service.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UserConsole processes user input and manages the logic for a coworking space management system.
 * This class handles user registration, authorization, and navigation through various commands for users and administrators.
 */
public class UserConsole {
    private UserService userService;
    private CoworkingSpaceService coworkingSpaceService;
    private BookingService bookingService;
    private Scanner in = new Scanner(System.in);
    private User currentUser;
    private boolean isAuthorized = false;

    /**
     * Constructs a UserConsole object with the necessary services.
     *
     * @param userService          User service instance for managing user-related operations.
     * @param coworkingSpaceService Coworking space service instance for managing space-related operations.
     * @param bookingService       Booking service instance for managing booking-related operations.
     */
    public UserConsole(UserService userService, CoworkingSpaceService coworkingSpaceService, BookingService bookingService) {
        this.userService = userService;
        this.coworkingSpaceService = coworkingSpaceService;
        this.bookingService = bookingService;
    }

    /**
     * Runs the initial commands loop of the console application.
     * Handles user registration, authorization, and main menu navigation based on user roles.
     */
    public void runStartCommands() {
        while (true) {
            try {

                if (isAuthorized) {
                    switch (currentUser.getRole()) {
                        case ADMIN -> runCoworkingSpaceCommands();
                        case USER -> runUserCommands();
                    }
                }

                ConsoleUI.printStartCommands();

                int command = in.nextInt();
                in.nextLine();

                switch (command) {
                    case 1 -> registration();
                    case 2 -> authorization();
                    case 3 -> {
                        in.close();
                        return;
                    }
                    default -> System.out.println("Неверная команда");
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Неверный формат ввода команды. Введите корректные данные");
                in.next();
            } catch (DateTimeParseException | IOException | SQLException e) {
                System.out.println("Некорректный формат ввода данных бронирования. Введите корректные данные " + e.getMessage());
            }
        }
    }

    /**
     * Handles the user registration process.
     * Prompts the user for username, password, and role selection to create a new user account.
     */
    private void registration() {
        System.out.print("Введите имя пользователя: ");
        String username = in.nextLine();
        User user = userService.findUserByName(username);

        if (user != null) {
            System.out.println("Пользователь с таким именем уже существует!");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = in.nextLine();
        ConsoleUI.printUserRoles();
        int userRole = in.nextInt();

        switch (userRole) {
            case 1 -> userService.register(new User(username, password, UserRole.USER));
            case 2 -> userService.register(new User(username, password, UserRole.ADMIN));
            default -> {
                System.out.println("Введена неверная комманда!");
                return;
            }
        }
        System.out.println("Регистрация прошла успешна!");
    }

    /**
     * Handles the user authorization process.
     * Prompts the user for username and password to authenticate and log in to the system.
     */
    private void authorization() {
        System.out.print("Введите имя пользователя: ");
        String username = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = in.nextLine();
        User user = userService.findUserByName(username);

        if (user == null) {
            System.out.println("Пользователя с таким именем не существует!");
            return;
        }

        boolean success = userService.login(user);
        if (success) {
            System.out.println("Авторизация прошла успешно.");
            currentUser = userService.findUserByName(username);
            isAuthorized = true;
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    /**
     * Runs the commands loop for regular user operations.
     * Provides options for viewing workplaces, conference halls, and managing bookings.
     */
    public void runUserCommands() throws IOException, SQLException {
        while (isAuthorized) {

            ConsoleUI.printUserCommands();

            int command = in.nextInt();
            in.nextLine();

            switch (command) {
                case 1 -> printCollection(coworkingSpaceService.getWorkplaces());
                case 2 -> printCollection(coworkingSpaceService.getAvailableConferenceHalls());
                case 3 -> runBookingCommands();
                case 4 -> {
                    isAuthorized = false;
                    currentUser = null;
                }
                default -> System.out.println("Неверная команда");
            }

        }
    }

    /**
     * Runs the commands loop for administrative operations in the coworking space.
     * Provides options for managing workplaces, conference halls, and bookings.
     */
    public void runCoworkingSpaceCommands() throws IOException, SQLException {
        while (isAuthorized) {

            ConsoleUI.printCoworkingSpaceCommands();

            int command = in.nextInt();
            in.nextLine();
            switch (command) {
                case 1 -> runWorkplaceCommands();
                case 2 -> runConferenceHallCommands();
                case 3 -> runBookingCommands();
                case 4 -> {
                    isAuthorized = false;
                    currentUser = null;
                    return;
                }
                default -> System.out.println("Неверная команда");
            }
        }
    }

    /**
     * Runs the commands loop for managing workplace operations.
     * Includes options for adding, updating, deleting workplaces, and managing bookings.
     */
    public void runWorkplaceCommands() throws IOException, SQLException {
        while (true) {

            ConsoleUI.printWorkplaceCommands();

            int command = in.nextInt();
            in.nextLine();

            switch (command) {
                case 1 -> {
                    System.out.println("Создание рабочего места");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    in.nextLine();
                    Workplace workplace = coworkingSpaceService.findWorkplaceById(id);

                    if (workplace != null) {
                        System.out.println("Рабочее место с таким id уже существует!");
                        continue;
                    }

                    System.out.print("Введите название: ");
                    String name = in.nextLine();
                    coworkingSpaceService.addWorkplace(new Workplace(id, name, true));
                }
                case 2 -> {
                    System.out.println("Обновление рабочего места");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    in.nextLine();
                    Workplace workplace = coworkingSpaceService.findWorkplaceById(id);

                    if (workplace == null) {
                        System.out.println("Рабочее место с таким id не существует!");
                        continue;
                    }

                    System.out.print("Введите название: ");
                    String name = in.nextLine();
                    ConsoleUI.printAvailableCommands();
                    int availableChoice = in.nextInt();
                    boolean isAvailable;

                    switch (availableChoice) {
                        case 1 -> isAvailable = true;
                        case 2 -> isAvailable = false;
                        default -> {
                            System.out.println("Произошла ошибка");
                            continue;
                        }
                    }

                    Workplace updatedWorkplace = new Workplace(id, name, isAvailable);
                    coworkingSpaceService.updateWorkplace(updatedWorkplace);
                }
                case 3 -> printCollection(coworkingSpaceService.getWorkplaces());
                case 4 -> printCollection(coworkingSpaceService.getAvailableWorkplaces());
                case 5 -> {
                    System.out.println("Удаление рабочего места");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    Workplace workplace = coworkingSpaceService.findWorkplaceById(id);

                    if (workplace != null) {
                        System.out.println("Рабочго места с таким id не существует!");
                        continue;
                    }

                    coworkingSpaceService.deleteWorkplace(id);
                }
                case 6 -> {
                    LocalDateTime[] dateTimes = BookingDateTimeInput();
                    if (dateTimes == null) { continue; }
                    System.out.println("Создание брони рабочего места");
                    System.out.print("Введите идентификатор рабочего места: ");
                    int id = in.nextInt();
                    in.nextLine();
                    Workplace workplace = coworkingSpaceService.findWorkplaceById(id);
                    if (workplace != null) {
                        LocalDateTime startDateTime = dateTimes[0];
                        LocalDateTime endDateTime = dateTimes[1];
                        Booking booking = new Booking(0, workplace.getId(), workplace.getName(), startDateTime, endDateTime, ResourceType.WORKPLACE, true);
                        bookingService.addBooking(booking);
                    } else {
                        System.out.println("Рабочего места с таким id не существует!");
                    }
                }
                case 7 -> {
                    System.out.println("Удаление брони");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    bookingService.deleteBooking(id);
                }
                case 8 -> printCollection(bookingService.getAllBookings());
                case 9 -> {
                    return;
                }
                default -> System.out.println("Неверная команда");

            }
        }
    }

    /**
     * Runs the commands loop for managing conference hall operations.
     * Includes options for adding, updating, deleting conference halls, and managing bookings.
     */
    public void runConferenceHallCommands() throws IOException, SQLException {
        while (true) {

            ConsoleUI.printConferenceHallCommands();

            int command = in.nextInt();
            in.nextLine();

            switch (command) {
                case 1 -> {
                    System.out.println("Создание конференц-зала");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    in.nextLine();
                    ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);

                    if (conferenceHall != null) {
                        System.out.println("Конференц-зал с таким id уже существует!");
                        continue;
                    }

                    System.out.print("Введите название: ");
                    String name = in.nextLine();
                    coworkingSpaceService.addConferenceHall(new ConferenceHall(id, name, true));
                }
                case 2 -> {
                    System.out.println("Обновление конференц-зала");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    in.nextLine();
                    ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);

                    if (conferenceHall != null) {
                        System.out.println("Конференц-зал с таким id не существует!");
                        continue;
                    }

                    System.out.print("Введите название: ");
                    String name = in.nextLine();
                    ConsoleUI.printAvailableCommands();
                    int availableChoice = in.nextInt();
                    boolean isAvailable;

                    switch (availableChoice) {
                        case 1 ->  isAvailable = true;
                        case 2 ->  isAvailable = false;
                        default -> {
                            System.out.println("Произошла ошибка");
                            continue;
                        }
                    }

                    ConferenceHall updatedConferenceHall = new ConferenceHall(id, name, isAvailable);
                    coworkingSpaceService.updateConferenceHall(updatedConferenceHall);
                }
                case 3 -> printCollection(coworkingSpaceService.getConferenceHalls());
                case 4 ->  printCollection(coworkingSpaceService.getAvailableConferenceHalls());
                case 5 -> {
                    System.out.println("Удаление конференц-зала");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();

                    ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);

                    if (conferenceHall != null) {
                        System.out.println("Конференц-зал с таким id не существует!");
                        continue;
                    }

                    coworkingSpaceService.deleteConferenceHall(id);
                }
                case 6 -> {
                    LocalDateTime[] dateTimes = BookingDateTimeInput();
                    if (dateTimes == null) { continue; }
                    System.out.println("Создание брони конференц хола");
                    System.out.print("Введите идентификатор конференц-хола: ");
                    int id = in.nextInt();
                    in.nextLine();
                    ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);

                    if (conferenceHall != null) {
                        LocalDateTime startDateTime = dateTimes[0];
                        LocalDateTime endDateTime = dateTimes[1];
                        Booking booking = new Booking(0, conferenceHall.getId(), conferenceHall.getName(), startDateTime, endDateTime, ResourceType.CONFERENCEHALL, true);
                        bookingService.addBooking(booking);
                    } else {
                        System.out.println("Конференц-зала с таким id не существует!");
                    }
                }
                case 7 -> {
                    System.out.println("Удаление брони");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    bookingService.deleteBooking(id);
                }
                case 8 -> printCollection(bookingService.getAllBookings());
                case 9 -> {
                    return;
                }
                default -> System.out.println("Неверная команда");
            }
        }
    }

    /**
     * Prompts the user to input booking date and time.
     *
     * @return An array of LocalDateTime objects representing start and end date/time of booking.
     */
    public LocalDateTime[] BookingDateTimeInput() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Введите дату начала бронирования (в формате YYYY-MM-DD):");
        String startDate = in.nextLine();
        LocalDate startLocalDate = LocalDate.parse(startDate, dateFormatter);

        System.out.println("Введите время начала бронирования (в формате HH:MM):");
        String startTime = in.nextLine();
        LocalTime startLocalTime = LocalTime.parse(startTime, timeFormatter);

        LocalDateTime startDateTime = LocalDateTime.of(startLocalDate, startLocalTime);

        System.out.println("Введите дату окончания бронирования (в формате YYYY-MM-DD):");
        String endDate = in.nextLine();
        LocalDate endLocalDate = LocalDate.parse(endDate, dateFormatter);

        System.out.println("Введите время окончания бронирования (в формате HH:MM):");
        String endTime = in.nextLine();
        LocalTime endLocalTime = LocalTime.parse(endTime, timeFormatter);

        LocalDateTime endDateTime = LocalDateTime.of(endLocalDate, endLocalTime);

        if (startDateTime.isAfter(endDateTime)) {
            System.out.println("Дата конца бронирования не может идти раньше начала!");
            return null;
        }
        return new LocalDateTime[]{startDateTime, endDateTime};
    }

    /**
     * Runs the commands loop for managing bookings.
     * Provides options for viewing, filtering, creating, and canceling bookings.
     */
    public void runBookingCommands() throws IOException, SQLException {
        while (true) {
            ConsoleUI.printBookingCommands();

            int command = in.nextInt();
            in.nextLine();

            switch (command) {
                case 1 -> printCollection(bookingService.getAllBookings());
                case 2 -> {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.println("Введите дату для фильтрации бронирования (в формате YYYY-MM-DD):");
                    String startDate = in.nextLine();
                    LocalDate startLocalDate = LocalDate.parse(startDate, dateFormatter);
                    printCollection(bookingService.filterBookingsByDate(startLocalDate));
                }
                case 3 -> {
                    System.out.print("Введите id пользователя для фильтрации бронирования: ");
                    String username = in.nextLine();
                    User filterUser = userService.findUserByName(username);
                    if (filterUser != null) {
                        printCollection(bookingService.filterBookingsByUser(filterUser));
                    } else {
                        System.out.println("Пользователь с таким username не найден!");
                    }
                }
                case 4 -> {
                    ConsoleUI.printResourceTypes();
                    int resourceType = in.nextInt();

                    switch (resourceType) {
                        case 1 -> printCollection(bookingService.filterBookingsByResource(ResourceType.WORKPLACE));
                        case 2 -> printCollection(bookingService.filterBookingsByResource(ResourceType.CONFERENCEHALL));
                        default -> System.out.println("Введена несуществующая команда!");
                    }
                }
                case 5 -> {
                    System.out.println("Бронирование");
                    System.out.print("Введите идентификатор брони: ");
                    int id = in.nextInt();
                    Booking foundBooking = bookingService.findBookingById(id);
                    if (foundBooking != null) {

                        if (foundBooking.getUserId() > 0 && foundBooking.getUserId() != currentUser.getId()) {
                            System.out.println("Данная бронь уже занята другим пользователем!");
                        } else {
                            foundBooking.setAvailable(false);
                            foundBooking.setUserId(currentUser.getId());
                            bookingService.updateBooking(foundBooking);
                        }

                    } else {
                        System.out.println("Бронирования с таким id не существует!");
                    }
                }
                case 6 -> {
                    System.out.println("Отмена брони");
                    System.out.print("Введите идентификатор: ");
                    int id = in.nextInt();
                    Booking foundBooking = bookingService.findBookingById(id);

                    if (foundBooking != null) {

                        if (foundBooking.getUserId() == 0) {
                            System.out.println("Свободная бронь не может быть отменена");

                        } else if (foundBooking.getUserId() != currentUser.getId()) {
                            System.out.println("Вы не можете отменить бронь другого пользователя");

                        } else {
                            foundBooking.setAvailable(true);
                            foundBooking.setUserId(0);
                            bookingService.updateBooking(foundBooking);
                        }

                    } else  {
                        System.out.println("Бронь с таким id не существует!");
                    }
                }
                case 7 -> {
                    return;
                }
                default -> System.out.println("Неверная команда");
            }
        }
    }

    /**
     * Prints the elements of an ArrayList to the console.
     *
     * @param collection The Collection containing elements to print.
     */
    public void printCollection(Collection<?> collection) {
        for (Object object : collection) {
            System.out.println(object);
        }
    }
}
