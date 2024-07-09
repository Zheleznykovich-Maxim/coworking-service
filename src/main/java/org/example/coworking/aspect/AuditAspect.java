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

/**
 * Aspect for auditing user actions.
 */
@Aspect
public class AuditAspect {
    private final UserActionService userActionService;

    /**
     * Constructor to initialize the user action service.
     *
     * @throws IOException if there is an issue with creating the user action service.
     */
    public AuditAspect() throws IOException {
        this.userActionService = new UserActionServiceFactory().create();
    }

    /**
     * Pointcut that matches methods annotated with @Auditable.
     */
    @Pointcut("@annotation(org.example.coworking.annotations.Auditable)")
    public void auditableMethods() {}

    /**
     * Advice that logs actions for methods annotated with @Auditable.
     *
     * @param joinPoint the join point providing reflective access to the method.
     * @throws SQLException if there is an issue with saving the audit log.
     */
    @AfterReturning("auditableMethods()")
    public void logAuditableAction(org.aspectj.lang.JoinPoint joinPoint) throws SQLException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Auditable auditable = method.getAnnotation(Auditable.class);
        String action = auditable.action();
        saveAuditLog(action);
    }

    /**
     * Saves the audit log with the specified action message.
     *
     * @param message the action message to be saved in the audit log.
     * @throws SQLException if there is an issue with saving the audit log.
     */
    private void saveAuditLog(String message) throws SQLException {
        UserAction userAction = new UserAction();
        userAction.setTimestamp(LocalDateTime.now());
        userAction.setAction(message);
        userActionService.saveAction(userAction);
    }
}
