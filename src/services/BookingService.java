package services;

import models.Booking;
import models.User;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingService {
    private ArrayList<Booking> bookingArrayList;
    private int nextBookingId;

    public BookingService(ArrayList<Booking> bookingArrayList) {
        this.bookingArrayList = bookingArrayList;
        this.nextBookingId = 1;
    }

    public ArrayList<Booking> getBookingArrayList() {
        return bookingArrayList;
    }


    public void setBookingArrayList(ArrayList<Booking> bookingArrayList) {
        this.bookingArrayList = bookingArrayList;
    }

    public int getNextBookingId() {
        return nextBookingId;
    }

    public void setNextBookingId(int nextBookingId) {
        this.nextBookingId = nextBookingId;
    }

    public void createBooking(User user,
                              int resourceId,
                              String resourceName,
                              LocalDateTime startTime,
                              LocalDateTime endTime) {
            Booking booking = new Booking(nextBookingId++, user, resourceId, resourceName, startTime, endTime);
            bookingArrayList.add(booking);
    }

    // Метод для отмены бронирования
    public void cancelBooking(int bookingId) {
        bookingArrayList.removeIf(booking -> booking.getId() == bookingId);
    }

    // Метод для просмотра всех бронирований
    public void viewAllBookings() {
        bookingArrayList.forEach(System.out::println);
    }

//    // Метод для фильтрации бронирований по дате
//    public ArrayList<Booking> filterBookingsByDate(LocalDateTime date) {
//        return (ArrayList<Booking>) bookingArrayList.stream()
//                .filter(booking -> booking.getStartTime().toLocalDate().equals(date.toLocalDate()))
//                .collect(Collectors.toList());
//    }

    // Метод для фильтрации бронирований по пользователю
//    public ArrayList<Booking> filterBookingsByUser(String user) {
//        return (ArrayList<Booking>) bookingArrayList.stream()
//                .filter(booking -> booking.getUser().equalsIgnoreCase(user))
//                .collect(Collectors.toList());
//    }

//    // Метод для фильтрации бронирований по ресурсу
//    public ArrayList<Booking> filterBookingsByResource(int resourceId) {
//        return (ArrayList<Booking>) bookingArrayList.stream()
//                .filter(booking -> booking.getResourceId() == resourceId)
//                .collect(Collectors.toList());
//    }

}
