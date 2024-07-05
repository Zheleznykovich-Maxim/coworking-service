package org.example.coworking.repository.query;

public class UserQuery {
    public static final String GET_ID_NEXT_USER = "SELECT nextval('coworking.user_seq')";
    public static final String ADD_USER = "INSERT INTO coworking.users " +
            "(id, username, password, role)" +
            "VALUES (?, ?, ?, ?)";
    public static final String FIND_USER_BY_USERNAME = "SELECT * FROM coworking.users WHERE username = ?";
}
