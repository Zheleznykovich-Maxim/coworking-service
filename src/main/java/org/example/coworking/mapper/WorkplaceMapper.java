package org.example.coworking.mapper;

import org.example.coworking.model.Workplace;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkplaceMapper {
    public static Workplace resultSetToWorkplace(ResultSet resultSet) throws SQLException {
        Workplace workplace = new Workplace();
        workplace.setId(resultSet.getInt("id"));
        workplace.setName(resultSet.getString("name"));
        workplace.setAvailable(resultSet.getBoolean("is_available"));
        return workplace;
    }
}
