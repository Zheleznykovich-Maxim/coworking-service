package org.example.coworking.console;

import org.example.coworking.enums.ResourceType;
import org.example.coworking.enums.UserRole;
import org.example.coworking.models.Booking;
import org.example.coworking.models.ConferenceHall;
import org.example.coworking.models.User;
import org.example.coworking.models.Workplace;
import org.example.coworking.services.BookingService;
import org.example.coworking.services.CoworkingSpaceService;
import org.example.coworking.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserConsole {
    private UserService userService;
    private CoworkingSpaceService coworkingSpaceService;
    private BookingService bookingService;
    private Scanner in = new Scanner(System.in);
    private User currentUser;
    boolean isAuthorized = false;

    public UserConsole(UserService userService, CoworkingSpaceService coworkingSpaceService, BookingService bookingService) {
        this.userService = userService;
        this.coworkingSpaceService = coworkingSpaceService;
        this.bookingService = bookingService;
    }

    public void runStartCommands() {

        while (true) {
            try {

                if (isAuthorized) {
                    switch (currentUser.getRole()) {
                        case ADMIN:
                            runCoworkingSpaceCommands();
                            break;
                        case USER:
                            runUserCommands();
                            break;
                    }
                }

                ConsoleUI.printStartCommands();

                int command = in.nextInt();
                in.nextLine();

                if (command == 1) {
                    registration();
                } else if (command == 2) {
                    authorization();
                } else if (command == 3) {
                    in.close();
                    return;
                } else {
                    System.out.println("Неверная команда");
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Неверный формат ввода команды. Введите корректные данные");
                in.next();
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Некорректный формат ввода данных бронирования. Введите корректные данные");
            }

        }
    }

    private void registration() {
        System.out.print("Введите имя пользователя: ");
        String username = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = in.nextLine();
        ConsoleUI.printUserRoles();
        int userRole = in.nextInt();
        boolean success;
        if (userRole == 1) {
            success = userService.register(username, password, UserRole.USER);
        } else if (userRole == 2) {
            success = userService.register(username, password, UserRole.ADMIN);
        } else {
            System.out.println("Введена неверная комманда!");
            return;
        }

        if (success) {
            System.out.println("Регистрация прошла успешно.");
        } else {
            System.out.println("Пользователь с таким именем уже существует.");
        }
    }

    private void authorization() {
        System.out.print("Введите имя пользователя: ");
        String username = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = in.nextLine();

        boolean success = userService.login(username, password);
        if (success) {
            System.out.println("Авторизация прошла успешно.");
            currentUser = userService.findUserByName(username);
            isAuthorized = true;
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    public void runUserCommands() {
        while (isAuthorized) {

            ConsoleUI.printUserCommands();

            int command = in.nextInt();
            in.nextLine();

            if (command == 1) {
                printArrayList(coworkingSpaceService.getWorkplaces());

            } else if (command == 2) {
                printArrayList(coworkingSpaceService.getAvailableConferenceHalls());

            } else if (command == 3) {
                runBookingCommands();

            } else if (command == 4) {
                isAuthorized = false;
                currentUser = null;

            } else {
                System.out.println("Неверная команда");
            }
        }

    }

    public void runCoworkingSpaceCommands() {
        while (isAuthorized) {

            ConsoleUI.printCoworkingSpaceCommands();

            int command = in.nextInt();
            in.nextLine();

            if (command == 1) {
                runWorkplaceCommands();

            } else if (command == 2) {
                runConferenceHallCommands();

            } else if (command == 3) {
                runBookingCommands();

            } else if (command == 4) {
                isAuthorized = false;
                currentUser = null;
                return;

            } else {
                System.out.println("Неверная команда");
            }
        }
    }
    public void runWorkplaceCommands() {
        while (true) {

            ConsoleUI.printWorkplaceCommands();

            int command = in.nextInt();
            in.nextLine(); // consume newline

            if (command == 1) {
                System.out.println("Создание рабочего места");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                System.out.print("Введите название: ");
                String name = in.nextLine();
                coworkingSpaceService.addWorkplace(new Workplace(id, name, true));

            } else if (command == 2) {
                System.out.println("Обновление рабочего места");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                System.out.print("Введите название: ");
                String name = in.nextLine();
                ConsoleUI.printAvailableCommands();
                int availableChoice = in.nextInt();
                boolean isAvailable;
                if (availableChoice == 1) { isAvailable = true; }
                else if (availableChoice == 2) { isAvailable = false; }
                else {
                    System.out.println("Произошла ошибка");
                    continue;
                }
                coworkingSpaceService.updateWorkplace(id, name, isAvailable);

            } else if (command == 3) {
                printArrayList(coworkingSpaceService.getWorkplaces());

            } else if (command == 4) {
                printArrayList(coworkingSpaceService.getAvailableWorkplaces());

            } else if (command == 5) {
                System.out.println("Удаление рабочего места");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                coworkingSpaceService.deleteWorkplace(id);
            } else if (command == 6) {
                LocalDateTime[] dateTimes = BookingDateTimeInput();
                if (dateTimes == null) { continue; }
                System.out.println("Создание брони рабочего места");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                Workplace workplace = coworkingSpaceService.findWorkplaceById(id);
                if (workplace != null) {
                    LocalDateTime startDateTime = dateTimes[0];
                    LocalDateTime endDateTime = dateTimes[1];

                    bookingService.createBooking(id, workplace.getName(), startDateTime, endDateTime, ResourceType.WORKSPACE);
                } else {
                    System.out.println("Рабочего места с таким id не существует!");
                }

            } else if (command == 7) {
                System.out.println("Удаление брони");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                bookingService.deleteBooking(id);
            } else if (command == 8) {
                bookingService.viewAllBookings();
            } else if (command == 9) {
                return;

            } else {
                System.out.println("Неверная команда");
            }
        }
    }

    public void runConferenceHallCommands() {
        while (true) {

            ConsoleUI.printConferenceHallCommands();

            int command = in.nextInt();
            in.nextLine(); // consume newline

            if (command == 1) {
                System.out.println("Создание конференц-зала");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                System.out.print("Введите название: ");
                String name = in.nextLine();
                coworkingSpaceService.addConferenceHall(new ConferenceHall(id, name, true));

            } else if (command == 2) {
                System.out.println("Обновление конференц-зала");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                System.out.print("Введите название: ");
                String name = in.nextLine();
                ConsoleUI.printAvailableCommands();
                int availableChoice = in.nextInt();
                boolean isAvailable;
                if (availableChoice == 1) { isAvailable = true; }
                else if (availableChoice == 2) { isAvailable = false; }
                else {
                    System.out.println("Произошла ошибка");
                    continue;
                }
                coworkingSpaceService.updateConferenceHall(id, name, isAvailable);

            } else if (command == 3) {
                printArrayList(coworkingSpaceService.getConferenceHalls());

            } else if (command == 4) {
                printArrayList(coworkingSpaceService.getAvailableConferenceHalls());

            } else if (command == 5) {
                System.out.println("Удаление конференц-зала");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                coworkingSpaceService.deleteConferenceHall(id);

            } else if (command == 6) {
                LocalDateTime[] dateTimes = BookingDateTimeInput();
                if (dateTimes == null) { continue; }
                System.out.println("Создание брони конференц хола");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                in.nextLine();
                ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);
                if (conferenceHall != null) {
                    LocalDateTime startDateTime = dateTimes[0];
                    LocalDateTime endDateTime = dateTimes[1];
                    // Создание бронирования
                    bookingService.createBooking(id, conferenceHall.getName(), startDateTime, endDateTime, ResourceType.CONFERENCEHALL);
                } else {
                    System.out.println("Конференц-зала с таким id не существует!");
                }

            } else if (command == 7) {
                System.out.println("Удаление брони");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                bookingService.deleteBooking(id);

            } else if (command == 8) {
                bookingService.viewAllBookings();

            } else if (command == 9) {
                return;

            } else {
                System.out.println("Неверная команда");
            }
        }
    }

    public void printArrayList(ArrayList<?> arrayList) {
        for (Object item : arrayList) {
            System.out.println(item);
        }
    }

    public LocalDateTime[] BookingDateTimeInput() {
        //бронь
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("Введите дату начала бронирования (в формате YYYY-MM-DD):");
        String startDate = in.nextLine();
        LocalDate startLocalDate = LocalDate.parse(startDate, dateFormatter);
        System.out.println("Введите время начала бронирования (в формате HH:MM):");
        String startTime = in.nextLine();
        LocalTime startLocalTime = LocalTime.parse(startTime, timeFormatter);

        LocalDateTime startDateTime = LocalDateTime.of(startLocalDate, startLocalTime);

        // Ввод даты и времени окончания бронирования
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
    public void runBookingCommands() {
        while (true) {
            ConsoleUI.printBookingCommands();

            int command = in.nextInt();
            in.nextLine();

            if (command == 1) {
                bookingService.viewAllBookings();

            } else if (command == 2) {
                //фильттр по дате
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                System.out.println("Введите дату для фильтрации бронирования (в формате YYYY-MM-DD):");
                String startDate = in.nextLine();
                LocalDate startLocalDate = LocalDate.parse(startDate, dateFormatter);
                printArrayList(bookingService.filterBookingsByDate(startLocalDate));
            } else if (command == 3) {
                //фильттр по пользователю
                System.out.print("Введите имя пользователя для фильтрации бронирования: ");
                String username = in.nextLine();
                User filterUser = userService.findUserByName(username);
                if (filterUser != null) {
                    printArrayList(bookingService.filterBookingsByUser(filterUser));
                } else {
                    System.out.println("Пользователь с таким username не найден!");
                }
            } else if (command == 4) {
                //фильтрация по ресурсу
                ConsoleUI.printResourceTypes();
                int resourceType = in.nextInt();
                if (resourceType == 1) {
                    printArrayList(bookingService.filterBookingsByResource(ResourceType.WORKSPACE));

                } else if (resourceType == 2) {
                    printArrayList(bookingService.filterBookingsByResource(ResourceType.CONFERENCEHALL));

                } else {
                    System.out.println("Введена несуществующая команда!");
                }

            } else if (command == 5) {
                System.out.println("Бронирование");
                System.out.print("Введите идентификатор брони: ");
                int id = in.nextInt();
                Booking foundBooking = bookingService.findBookingById(id);
                if (foundBooking != null) {

                    if (foundBooking.getUser() != null && !foundBooking.getUser().equals(currentUser)) {
                        System.out.println("Данная бронь уже занята другим пользователем!");
                    } else {
                        foundBooking.setAvailable(false);
                        foundBooking.setUser(currentUser);
                    }

                } else {
                    System.out.println("Бронирования с таким id не существует!");
                }
            } else if (command == 6) {
                System.out.println("Отмена брони");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                Booking foundBooking = bookingService.findBookingById(id);

                if (foundBooking != null) {

                    if (foundBooking.getUser() == null) {
                        System.out.println("Свободная бронь не может быть отменена");

                    } else if (!foundBooking.getUser().equals(currentUser)) {
                        System.out.println("Вы не можете отменить бронь другого пользователя");

                    } else {
                        foundBooking.setAvailable(true);
                        foundBooking.setUser(null);
                    }

                } else  {
                    System.out.println("Бронь с таким id не существует!");
                }

            } else if (command == 7) {
                isAuthorized = false;
                currentUser = null;
                return;

            } else {
                System.out.println("Неверная команда");
            }
        }
    }
}
