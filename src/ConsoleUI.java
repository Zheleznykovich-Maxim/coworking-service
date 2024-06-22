public class ConsoleUI {

    public static void printUserCommands() {
        System.out.print("""
                    Выберите команду:
                    1 - Регистрация
                    2 - Авторизация
                    3 - Выход
                    """);
    }

    public static void printCoworkingSpaceCommands() {
        System.out.print("""
                    Выберите команду:
                    1 - Рабочее место
                    2 - Конференц-зал
                    3 - Выход из пользователя
                    """);
    }

    public static void printWorkplaceCommands() {
        System.out.print("""
                    Выберите команду для рабочего места:
                    1 - Создать
                    2 - Изменить
                    3 - Показать все
                    4 - Показать свободные
                    5 - Удалить
                    6 - Вернуться назад
                    """);
    }

    public static void printConferenceHallCommands() {
        System.out.print("""
                    Выберите команду для конференц-зала:
                    1 - Создать
                    2 - Изменить
                    3 - Показать все
                    4 - Показать свободные
                    5 - Удалить
                    6 - Вернуться назад
                    """);
    }

    public static void printAvailableCommands() {
        System.out.print("""
                    Вы освобождаете помещение?
                    1 - Да
                    2 - Нет
                    """);
    }
}
