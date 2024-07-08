package org.example.coworking.service;

import lombok.RequiredArgsConstructor;
import org.example.coworking.model.UserAction;
import org.example.coworking.repository.UserActionRepository;
import java.sql.SQLException;

@RequiredArgsConstructor
public class UserActionService {
    private final UserActionRepository userActionRepository;

    public void saveAction(UserAction userAction) throws SQLException {
        userActionRepository.saveUserAction(userAction);
    }
}
