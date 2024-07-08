package org.example.coworking.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.coworking.annotations.Auditable;
import org.example.coworking.factory.UserActionServiceFactory;
import org.example.coworking.model.UserAction;
import org.example.coworking.service.UserActionService;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Aspect
public class AuditAspect {
    private final UserActionService userActionService;

    public AuditAspect() throws IOException {
        this.userActionService = new UserActionServiceFactory().create();
    }

    // Точка среза для методов, аннотированных @Auditable
    @Pointcut("@annotation(org.example.coworking.annotations.Auditable)")
    public void auditableMethods() {}

    // Перехватываем выполнение аннотированных методов и сохраняем аудитную запись
    @AfterReturning("auditableMethods()")
    public void logAuditableAction(org.aspectj.lang.JoinPoint joinPoint) throws SQLException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Auditable auditable = method.getAnnotation(Auditable.class);
        String action = auditable.action();
        saveAuditLog(action);
    }

    private void saveAuditLog(String message) throws SQLException {
        UserAction userAction = new UserAction();
        userAction.setTimestamp(LocalDateTime.now());
        userAction.setAction(message);
        userActionService.saveAction(userAction);
    }
}
