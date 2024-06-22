import models.ConferenceHall;
import models.Workplace;
import services.UserService;
import java.util.ArrayList;
import java.util.Scanner;

public class UserConsole {
    private UserService userService;
    private CoworkingSpace coworkingSpace;
    private Scanner in = new Scanner(System.in);
    boolean isAuthorized = false;

    public UserConsole(UserService userService, CoworkingSpace coworkingSpace) {
        this.userService = userService;
        this.coworkingSpace = coworkingSpace;
    }

    public void runUserCommands() {

        while (true) {

            if (isAuthorized) {
                runCoworkingSpaceCommands();
            }

            ConsoleUI.printUserCommands();

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
        }
    }

    private void registration() {
        System.out.print("Введите имя пользователя: ");
        String username = in.nextLine();
        System.out.print("Введите пароль: ");
        String password = in.nextLine();

        boolean success = userService.register(username, password);
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
            isAuthorized = true;
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
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
                isAuthorized = false;
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
                coworkingSpace.addWorkplace(new Workplace(id, name, true));

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
                coworkingSpace.updateWorkplace(id, name, isAvailable);

            } else if (command == 3) {
                printArrayList(coworkingSpace.getWorkplaces());

            } else if (command == 4) {
                printArrayList(coworkingSpace.getAvailableWorkplaces());

            } else if (command == 5) {
                System.out.println("Удаление рабочего места");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                coworkingSpace.deleteWorkplace(id);

            } else if (command == 6) {
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
                coworkingSpace.addConferenceHall(new ConferenceHall(id, name, true));

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
                coworkingSpace.updateConferenceHall(id, name, isAvailable);

            } else if (command == 3) {
                printArrayList(coworkingSpace.getConferenceHalls());

            } else if (command == 4) {
                printArrayList(coworkingSpace.getAvailableConferenceHalls());

            } else if (command == 5) {
                System.out.println("Удаление конференц-зала");
                System.out.print("Введите идентификатор: ");
                int id = in.nextInt();
                coworkingSpace.deleteConferenceHall(id);

            } else if (command == 6) {
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
}
