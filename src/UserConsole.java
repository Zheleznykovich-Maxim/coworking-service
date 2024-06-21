import java.util.Scanner;

public class UserConsole {
    private UserService userService;
    private Scanner in = new Scanner(System.in);
    public UserConsole(UserService userService) {
        this.userService = userService;
    }

    public void run() {
        while (true) {

            System.out.print("""
                    Выберите команду:
                    1 - Регистрация
                    2 - Авторизация
                    3 - Выход
                    """);

            int command = in.nextInt();
            in.nextLine(); // consume newline

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
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }
}
