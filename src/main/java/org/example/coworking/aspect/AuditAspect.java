package org.example.coworking.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.coworking.factory.UserActionRepositoryFactory;
import org.example.coworking.model.UserAction;
import org.example.coworking.repository.UserActionRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Aspect
public class AuditAspect {
    private final UserActionRepository userActionRepository;

    public AuditAspect() throws IOException {
        this.userActionRepository = new UserActionRepositoryFactory().create();
    }

    // Точка среза для метода login
    @Pointcut("execution(public * org.example.coworking.service.UserService.login(..))")
    public void loginMethod() {}

    // Точка среза для метода register
    @Pointcut("execution(public * org.example.coworking.service.UserService.register(..))")
    public void registerMethod() {}

    // Точка среза для метода addBooking
    @Pointcut("execution(public * org.example.coworking.service.BookingService.addBooking(..))")
    public void addBookingMethod() {}

    // Точка среза для метода deleteBooking
    @Pointcut("execution(public * org.example.coworking.service.BookingService.deleteBooking(..))")
    public void deleteBookingMethod() {}

    // Перехватываем выполнение методов и сохраняем аудитную запись
    @AfterReturning("loginMethod()")
    public void logLoginAction() throws SQLException {
        saveAuditLog("Login action", "Авторизация пользователя");
    }

    @AfterReturning("registerMethod()")
    public void logRegisterAction() throws SQLException {
        saveAuditLog("Register action", "Регистрация пользователя");
    }

    @AfterReturning("addBookingMethod()")
    public void logAddBookingAction() throws SQLException {
        saveAuditLog("Add booking action", "Добавление брони");
    }

    @AfterReturning("deleteBookingMethod()")
    public void logDeleteBookingAction() throws SQLException {
        saveAuditLog("Delete booking action", "Удаление брони");
    }

    private void saveAuditLog(String actionType, String action) throws SQLException {
        UserAction userAction = new UserAction();
        userAction.setTimestamp(LocalDateTime.now());
        userAction.setAction(action);
        userActionRepository.save(userAction);
    }

    private String getCurrentUsername() {
        // Логика определения текущего пользователя, например, из контекста аутентификации
        return "test_user"; // Замените на реальную логику
    }
}
