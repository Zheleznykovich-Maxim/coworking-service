package org.example.coworking.out;

/**
 * The {@code ConsoleUI} class provides static methods for printing various command menus to the console.
 * These methods are used to display different options to the user in a console-based coworking space management application.
 */
public class ConsoleUI {

    /**
     * Prints the start commands menu.
     */
    public static void printStartCommands() {
        System.out.print("""
                Выберите команду:
                1 - Регистрация
                2 - Авторизация
                3 - Выход
                """);
    }

    /**
     * Prints the user commands menu.
     */
    public static void printUserCommands() {
        System.out.print("""
                Выберите команду:
                1 - Показать рабочие места
                2 - Показать конференц залы
                3 - Бронирование
                4 - Выход из пользователя
                """);
    }

    /**
     * Prints the coworking space commands menu.
     */
    public static void printCoworkingSpaceCommands() {
        System.out.print("""
                Выберите команду:
                1 - Рабочее место
                2 - Конференц-зал
                3 - Бронь
                4 - Выход из пользователя
                """);
    }

    /**
     * Prints the workplace commands menu.
     */
    public static void printWorkplaceCommands() {
        System.out.print("""
                Выберите команду для рабочего места:
                1 - Создать
                2 - Обновить
                3 - Показать все
                4 - Показать свободные
                5 - Удалить
                6 - Создать бронь
                7 - Удалить бронь
                8 - Показать все брони
                9 - Вернуться назад
                """);
    }

    /**
     * Prints the conference hall commands menu.
     */
    public static void printConferenceHallCommands() {
        System.out.print("""
                Выберите команду для конференц-зала:
                1 - Создать
                2 - Обновить
                3 - Показать все
                4 - Показать свободные
                5 - Удалить
                6 - Создать бронь
                7 - Удалить бронь
                8 - Показать все брони
                9 - Вернуться назад
                """);
    }

    /**
     * Prints the available commands menu.
     */
    public static void printAvailableCommands() {
        System.out.print("""
                Вы освобождаете помещение?
                1 - Да
                2 - Нет
                """);
    }

    /**
     * Prints the booking commands menu.
     */
    public static void printBookingCommands() {
        System.out.print("""
                Выберите команду для бронирования:
                1 - Показать всю бронь
                2 - Фильтр по дате
                3 - Фильтр по пользователю
                4 - Фильтр по ресурсу
                5 - Забронировать
                6 - Отменить бронь
                7 - Вернуться назад
                """);
    }

    /**
     * Prints the resource types menu.
     */
    public static void printResourceTypes() {
        System.out.print("""
                Выберите тип ресурса
                1 - Workspace
                2 - Conference Hall
                """);
    }

    /**
     * Prints the user roles menu.
     */
    public static void printUserRoles() {
        System.out.print("""
                Выберите вашу роль
                1 - Пользователь
                2 - Администратор
                """);
    }
}
