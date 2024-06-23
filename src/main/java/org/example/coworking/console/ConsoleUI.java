package org.example.coworking.console;

public class ConsoleUI {

    public static void printStartCommands() {
        System.out.print("""
                    Выберите команду:
                    1 - Регистрация
                    2 - Авторизация
                    3 - Выход
                    """);
    }

    public static void printUserCommands() {
        System.out.print("""
                    Выберите команду:
                    1 - Показать рабочие места
                    2 - Показать конференц залы
                    3 - Бронирование
                    4 - Выход из пользователя
                    """);
    }

    public static void printCoworkingSpaceCommands() {
        System.out.print("""
                    Выберите команду:
                    1 - Рабочее место
                    2 - Конференц-зал
                    3 - Бронь
                    4 - Выход из пользователя
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
                    6 - Создать бронь
                    7 - Удалить бронь
                    8 - Показать все брони
                    9 - Вернуться назад
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
                    6 - Создать бронь
                    7 - Удалить бронь
                    8 - Показать все брони
                    9 - Вернуться назад
                    """);
    }

    public static void printAvailableCommands() {
        System.out.print("""
                    Вы освобождаете помещение?
                    1 - Да
                    2 - Нет
                    """);
    }

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

    public static void printResourceTypes() {
        System.out.print("""
                    Выберите тип ресурса
                    1 - Workspace
                    2 - Conference Hall
                    """);
    }

    public static void printUserRoles() {
        System.out.print("""
                    Выберите вашу роль
                    1 - Пользователь
                    2 - Администратор
                    """);
    }
}
