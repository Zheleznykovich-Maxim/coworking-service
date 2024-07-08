# Coworking Space Management Application

## Описание
Это приложение предназначено для управления коворкинг-пространством

## Запуск
Необходимо запустить класс `Main.Java`
## Тестирование
Необходимо перейти в папку `test`, нажать <b>ПКМ</b> и запустить все тесты
## Взаимодействие с программой
1. Запустить миграции: необходимо запустить класс `Main.Java`.

2. Запустить команду Maven `package`.

3. Скопировать папку `coworking` из `target` и вставить в `webapps` вашего Tomcat.

4. Проверить наличие JDBC драйвера в папке `lib` вашего Tomcat. При его отстуствии скачать его и вставить в папку `lib`.

Ссылка для скаичвания: https://jdbc.postgresql.org/download/ 
Пример названия файла драйвера: `postgresql-42.7.3.jar`.

5. Для запуска Tomcat: запустить файл `startup.bat`.

Теперь приложение готово получать запросы!

<b>При попытке первого запроса может выскочить ошибка со статусом 500, но если отправить запрос ещё раз то всё зарабаотает!</b>

6. Для остановки Tomcat: запустить файл `shutdown.bat`.

## Примеры запросов
WORKPLACE
Для получения всех рабочих мест: GET http://localhost:8080/coworking/workplace
Для получения рабочего места с id равным 1: GET http://localhost:8080/coworking/workplace/1
Для измненеия рабочего места с id равным 1: PUT http://localhost:8080/coworking/workplace/1
req body: {
    "name" : "Example",
    "isAvailable": false
}
Для удаления рабочего места по id равынм 1: DELETE http://localhost:8080/coworking/workplace/1
Для создания рабочего места: POST http://localhost:8080/coworking/workplace
req body {
    "name" : "Example",
    "isAvailable": false
}

CONFERENCEHALL
Для получения всех конференц-залов: GET http://localhost:8080/coworking/conference-hall
Для получения конференц-зала с id равным 1: GET http://localhost:8080/coworking/conference-hall/1
Для измненеия конференц-зала с id равным 1: PUT http://localhost:8080/coworking/conference-hall/1
req body: {
    "name" : "Example",
    "isAvailable": false
}
Для удаления конференц-зала по id равынм 1: DELETE http://localhost:8080/coworking/workplace/1
Для создания конференц-зала: POST http://localhost:8080/coworking/conference-hall
req body {
    "name" : "Example",
    "isAvailable": false
}

BOOKING
Для получения всех броней: GET http://localhost:8080/coworking/coworking
Для получения брони с id равным 1: GET http://localhost:8080/coworking/booking/1
Для измненеия брони с id равным 1: PUT http://localhost:8080/coworking/booking/1
req body {
    "userId": 2,
    "resourceId": 2,
    "startTime": "2024-06-01T10:00:00",
    "endTime": "2024-07-01T10:00:00",
    "resourceType": "WORKPLACE",
    "available": true
}
Для удаления брони по id равынм 1: DELETE http://localhost:8080/coworking/booking/1
Для создания брони: POST http://localhost:8080/coworking/booking
req body {
    "userId": 2,
    "resourceId": 2,
    "startTime": "2024-06-01T10:00:00",
    "endTime": "2024-07-01T10:00:00",
    "resourceType": "WORKPLACE",
    "available": true
}

USER
Для регистрации пользователя: POST http://localhost:8080/coworking/user/register
req body {
    "username": "username",
    "password": "password",
    "role": "ADMIN"
}
Для авторизации пользователя: POST http://localhost:8080/coworking/user/login
req body {
    "username": "username",
    "password": "password"
}
