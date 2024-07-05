package org.example.coworking.in;

import org.example.coworking.factory.UserConsoleFactory;

public class UserConsoleStarter {
    public static void startUserConsole() {
        UserConsole userConsole = new UserConsoleFactory().create();
        userConsole.runStartCommands();
    }
}
