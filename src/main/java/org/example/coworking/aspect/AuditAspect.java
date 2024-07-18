package org.example.coworking.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.coworking.annotations.Auditable;
import org.example.coworking.domain.model.UserAction;
import org.example.coworking.service.impl.UserActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Aspect for auditing user actions.
 */

@Aspect
@Component
public class AuditAspect {
    private final UserActionServiceImpl userActionServiceImpl;

    @Autowired
    public AuditAspect(UserActionServiceImpl userActionServiceImpl) {
        this.userActionServiceImpl = userActionServiceImpl;
    }

    @Before("@annotation(auditable)")
    public void audit(JoinPoint joinPoint, Auditable auditable) {
        String methodName = joinPoint.getSignature().getName();
        String message = auditable.action();
        saveAuditLog(message);
    }

    private void saveAuditLog(String message) {
        UserAction userAction = new UserAction();
        userAction.setTimestamp(LocalDateTime.now());
        userAction.setAction(message);
        userActionServiceImpl.saveAction(userAction);
    }
}
