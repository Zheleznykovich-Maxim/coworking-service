package org.example.coworking;

import org.example.coworking.config.LiquibaseConfig;
import org.example.coworking.in.UserConsoleStarter;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        LiquibaseConfig.runMigrations();
    }
}