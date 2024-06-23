package org.example.coworking;

import org.example.coworking.console.UserConsole;
import org.example.coworking.models.Booking;
import org.example.coworking.models.ConferenceHall;
import org.example.coworking.models.Workplace;
import org.example.coworking.services.BookingService;
import org.example.coworking.services.CoworkingSpaceService;
import org.example.coworking.services.UserService;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ArrayList<Workplace> workplaces = new ArrayList<>();
        ArrayList<ConferenceHall> conferenceHalls = new ArrayList<>();
        CoworkingSpaceService coworkingSpaceService = new CoworkingSpaceService(workplaces, conferenceHalls);
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        BookingService bookingService = new BookingService(bookingArrayList);
        UserConsole userConsole = new UserConsole(userService, coworkingSpaceService, bookingService);
        userConsole.runStartCommands();
    }
}