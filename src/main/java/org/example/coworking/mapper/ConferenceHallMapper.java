package org.example.coworking.mapper;

import org.example.coworking.model.ConferenceHall;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceHallMapper {
    public static ConferenceHall resultSetToConferenceHall(ResultSet resultSet) throws SQLException {
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setId(resultSet.getInt("id"));
        conferenceHall.setName(resultSet.getString("name"));
        conferenceHall.setAvailable(resultSet.getBoolean("is_available"));
        return conferenceHall;
    }
}
