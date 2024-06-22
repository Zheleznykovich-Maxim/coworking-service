import models.ConferenceHall;
import models.Workplace;
import services.UserService;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ArrayList<Workplace> workplaces = new ArrayList<>();
        ArrayList<ConferenceHall> conferenceHalls = new ArrayList<>();
        CoworkingSpace coworkingSpace = new CoworkingSpace(workplaces, conferenceHalls);
        UserConsole userConsole = new UserConsole(userService, coworkingSpace);
        userConsole.runUserCommands();
    }
}