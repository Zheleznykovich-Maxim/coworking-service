package org.example.coworking.service.impl;

import org.example.coworking.domain.model.UserAction;
import org.example.coworking.repository.UserActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

/**
 * Service class for managing user actions.
 */
@Service
public class UserActionService {
    private final UserActionRepository userActionRepository;

    @Autowired
    public UserActionService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    /**
     * Saves a user action using the provided repository.
     *
     * @param userAction the user action to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveAction(UserAction userAction) {
        try {
            userActionRepository.saveUserAction(userAction);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user action", e);
        }
    }
}
