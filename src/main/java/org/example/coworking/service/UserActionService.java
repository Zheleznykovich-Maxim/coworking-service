package org.example.coworking.service;

import lombok.RequiredArgsConstructor;
import org.example.coworking.model.UserAction;
import org.example.coworking.repository.UserActionRepository;
import java.sql.SQLException;

/**
 * Service class for managing user actions.
 */
@RequiredArgsConstructor
public class UserActionService {
    private final UserActionRepository userActionRepository;

    /**
     * Saves a user action using the provided repository.
     *
     * @param userAction the user action to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveAction(UserAction userAction) throws SQLException {
        userActionRepository.saveUserAction(userAction);
    }
}
