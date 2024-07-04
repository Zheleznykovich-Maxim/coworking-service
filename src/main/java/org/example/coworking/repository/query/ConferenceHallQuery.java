package org.example.coworking.repository.query;

public class ConferenceHallQuery {
    public static final String GET_ALL_CONFERENCE_HALLS = "SELECT * FROM coworking.conference_halls";
    public static final String GET_ID_NEXT_CONFERENCE_HALL = "SELECT nextval('coworking.conference_hall_seq')";
    public static final String ADD_CONFERENCE_HALL = "INSERT INTO coworking.conference_halls " +
            "(id, name, is_available)" +
            "VALUES (?, ?, ?)";
    public static final String DELETE_CONFERENCE_HALL = "DELETE FROM coworking.conference_halls WHERE id = ?";
    public static final String FIND_CONFERENCE_HALL_BY_ID = "SELECT * FROM coworking.conference_halls WHERE id = ?";
    public static final String UPDATE_CONFERENCE_HALL = "UPDATE coworking.conference_halls SET name = ?, is_available = ? WHERE id = ?";
    public static final String GET_ALL_AVAILABLE_CONFERENCE_HALLS = "SELECT * FROM coworking.conference_halls WHERE is_available = true";
}
