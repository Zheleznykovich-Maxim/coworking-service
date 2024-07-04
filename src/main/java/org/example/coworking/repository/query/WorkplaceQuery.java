package org.example.coworking.repository.query;

public class WorkplaceQuery {
    public static final String GET_ALL_WORKPLACES = "SELECT * FROM coworking.workplaces";
    public static final String GET_ID_NEXT_WORKPLACE = "SELECT nextval('coworking.workplace_seq')";
    public static final String ADD_WORKPLACE = "INSERT INTO coworking.workplaces " +
            "(id, name, is_available)" +
            "VALUES (?, ?, ?)";
    public static final String DELETE_WORKPLACE = "DELETE FROM coworking.workplaces WHERE id = ?";
    public static final String FIND_WORKPLACE_BY_ID = "SELECT * FROM coworking.workplaces WHERE id = ?";
    public static final String UPDATE_WORKPLACE = "UPDATE coworking.workplaces SET name = ?, is_available = ? WHERE id = ?";
    public static final String GET_ALL_AVAILABLE_WORKPLACES = "SELECT * FROM coworking.workplaces WHERE is_available = true";
}
