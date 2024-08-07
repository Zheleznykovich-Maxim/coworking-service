# Coworking Space Management Application

## Описание
Это приложение предназначено для управления коворкинг-пространством

## Взаимодействие с программой
1. Необходимо запустить `build` приложения coworking.
2. Запустить команду плагина Maven war `war:war`.
3. Скопировать папку `coworking.war` из `target` и вставить в `webapps` вашего Tomcat.
4. Проверить наличие JDBC драйвера в папке `lib` вашего Tomcat. При его отстуствии скачать его и вставить в папку `lib`.
   
Ссылка для скаичвания: https://jdbc.postgresql.org/download/ 
Пример названия файла драйвера: `postgresql-42.7.3.jar`.

5. Для запуска Tomcat: запустить файл `startup.bat`.

Теперь приложение готово получать запросы!

6. Для остановки Tomcat: запустить файл `shutdown.bat`.
   
## Тестирование
Необходимо перейти в папку `test`, нажать <b>ПКМ</b> и запустить все тесты

## Примеры запросов
WORKPLACE
1. Для получения всех рабочих мест: GET http://localhost:8080/coworking/workplace
2. Для получения рабочего места с id равным 1: GET http://localhost:8080/coworking/workplace/1
3. Для измненеия рабочего места с id равным 1: PUT http://localhost:8080/coworking/workplace/1
req body: {
    "name" : "Example",
    "isAvailable": false
}
4. Для удаления рабочего места по id равынм 1: DELETE http://localhost:8080/coworking/workplace/1
5. Для создания рабочего места: POST http://localhost:8080/coworking/workplace
req body {
    "name" : "Example",
    "isAvailable": false
}

CONFERENCEHALL
1. Для получения всех конференц-залов: GET http://localhost:8080/coworking/conference-hall
2. Для получения конференц-зала с id равным 1: GET http://localhost:8080/coworking/conference-hall/1
3. Для измненеия конференц-зала с id равным 1: PUT http://localhost:8080/coworking/conference-hall/1
req body: {
    "name" : "Example",
    "isAvailable": false
}
4. Для удаления конференц-зала по id равынм 1: DELETE http://localhost:8080/coworking/workplace/1
5. Для создания конференц-зала: POST http://localhost:8080/coworking/conference-hall
req body {
    "name" : "Example",
    "isAvailable": false
}

BOOKING
1. Для получения всех броней: GET http://localhost:8080/coworking/coworking
2. Для получения брони с id равным 1: GET http://localhost:8080/coworking/booking/1
3. Для измненеия брони с id равным 1: PUT http://localhost:8080/coworking/booking/1
req body {
    "userId": 2,
    "resourceId": 2,
    "startTime": "2024-06-01T10:00:00",
    "endTime": "2024-07-01T10:00:00",
    "resourceType": "WORKPLACE",
    "available": true
}
4. Для удаления брони по id равынм 1: DELETE http://localhost:8080/coworking/booking/1
5. Для создания брони: POST http://localhost:8080/coworking/booking
req body {
    "userId": 2,
    "resourceId": 2,
    "startTime": "2024-06-01T10:00:00",
    "endTime": "2024-07-01T10:00:00",
    "resourceType": "WORKPLACE",
    "available": true
}

USER
1. Для регистрации пользователя: POST http://localhost:8080/coworking/user/register
req body {
    "username": "username",
    "password": "password",
    "role": "ADMIN"
}
2. Для авторизации пользователя: POST http://localhost:8080/coworking/user/login
req body {
    "username": "username",
    "password": "password"
}
