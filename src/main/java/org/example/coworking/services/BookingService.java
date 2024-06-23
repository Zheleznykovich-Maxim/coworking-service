package org.example.coworking.services;

import org.example.coworking.enums.ResourceType;
import org.example.coworking.models.Booking;
import org.example.coworking.models.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public void createBooking(
                              int resourceId,
                              String resourceName,
                              LocalDateTime startTime,
                              LocalDateTime endTime,
                              ResourceType resourceType) {
            Booking booking = new Booking(nextBookingId++, resourceId, resourceName, startTime, endTime, resourceType);
            bookingArrayList.add(booking);
    }

    // Метод для отмены бронирования
    public void deleteBooking(int bookingId) {
        bookingArrayList.removeIf(booking -> booking.getId() == bookingId);
    }

    // Метод для просмотра всех бронирований
    public void viewAllBookings() {
        bookingArrayList.forEach(System.out::println);
    }

    // Метод для фильтрации бронирований по дате
    public ArrayList<Booking> filterBookingsByDate(LocalDate date) {
        return (ArrayList<Booking>) bookingArrayList.stream()
                .filter(booking -> booking.getStartTime().toLocalDate().equals(date) ||
                        booking.getEndTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    // Метод для фильтрации бронирований по пользователю
    public ArrayList<Booking> filterBookingsByUser(User user) {
        return (ArrayList<Booking>) bookingArrayList.stream()
                .filter(booking -> booking.getUser().equals(user))
                .collect(Collectors.toList());
    }

    // Метод для фильтрации бронирований по ресурсу
    public ArrayList<Booking> filterBookingsByResource(ResourceType resourceType) {
        return (ArrayList<Booking>) bookingArrayList.stream()
                .filter(booking -> booking.getResourceType().equals(resourceType))
                .collect(Collectors.toList());
    }

    public Booking findBookingById(int id) {
        for (Booking booking : bookingArrayList) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

}
