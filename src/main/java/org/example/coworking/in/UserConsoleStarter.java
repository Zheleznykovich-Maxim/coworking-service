package org.example.coworking.in;

import org.example.coworking.factory.UserConsoleFactory;
import java.io.IOException;

public class UserConsoleStarter {
    public static void startUserConsole() throws IOException {
        UserConsole userConsole = new UserConsoleFactory().create();
        userConsole.runStartCommands();
    }
}
