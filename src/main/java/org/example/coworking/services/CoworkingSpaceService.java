package org.example.coworking.services;

import org.example.coworking.models.ConferenceHall;
import org.example.coworking.models.Workplace;

import java.util.ArrayList;

public class CoworkingSpaceService {
    private ArrayList<Workplace> workplaces;
    private ArrayList<ConferenceHall> conferenceHalls;

    public CoworkingSpaceService(ArrayList<Workplace> workplaces,
                                 ArrayList<ConferenceHall> conferenceHalls) {
        this.workplaces = workplaces;
        this.conferenceHalls = conferenceHalls;
    }

    public ArrayList<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(ArrayList<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public ArrayList<ConferenceHall> getConferenceHalls() {
        return conferenceHalls;
    }

    public void setConferenceHalls(ArrayList<ConferenceHall> conferenceHalls) {
        this.conferenceHalls = conferenceHalls;
    }

    public void addWorkplace(Workplace workplace) {
        workplaces.add(workplace);
    }

    public void addConferenceHall(ConferenceHall conferenceHall) {
        conferenceHalls.add(conferenceHall);
    }

    public ArrayList<Workplace> getAvailableWorkplaces() {
        ArrayList<Workplace> availableWorkplaces = new ArrayList<>();
        for (Workplace workplace : workplaces) {
            if (workplace.isAvailable()) {
                availableWorkplaces.add(workplace);
            }
        }
        return availableWorkplaces;
    }

    public ArrayList<ConferenceHall> getAvailableConferenceHalls() {
        ArrayList<ConferenceHall> availableConferenceHalls = new ArrayList<>();
        for (ConferenceHall conferenceHall : conferenceHalls) {
            if (conferenceHall.isAvailable()) {
                availableConferenceHalls.add(conferenceHall);
            }
        }
        return availableConferenceHalls;
    }

    public void updateConferenceHall(int id, String name, boolean isAvailable) {
        ConferenceHall conferenceHall = findConferenceHallById(id);
        if (conferenceHall != null) {
            conferenceHall.setAvailable(isAvailable);
            conferenceHall.setName(name);
            System.out.println("Обновление прошло успешно");
        } else {
            System.out.println("Ошибка: не найдено помещение с таким id");
        }
    }

    public void updateWorkplace(int id, String name, boolean isAvailable) {
        Workplace workplace = findWorkplaceById(id);
        if (workplace != null) {
            workplace.setAvailable(isAvailable);
            workplace.setName(name);
            System.out.println("Обновление прошло успешно");
        } else {
            System.out.println("Ошибка: не найдено помещение с таким id");
        }
    }

    public void deleteConferenceHall(int id) {
        ConferenceHall conferenceHall = findConferenceHallById(id);
        if (conferenceHall != null) {
            conferenceHalls.remove(conferenceHall);
            System.out.println("Удаление прошло успешно");
        } else {
            System.out.println("Ошибка: не найдено помещение с таким id");
        }
    }

    public void deleteWorkplace(int id) {
        Workplace workplace = findWorkplaceById(id);
        if (workplace != null) {
            workplaces.remove(workplace);
            System.out.println("Удаление прошло успешно");
        } else {
            System.out.println("Ошибка: не найдено помещение с таким id");
        }
    }

    public Workplace findWorkplaceById(int id) {
        for (Workplace workplace : workplaces) {
            if (workplace.getId() == id) {
                return workplace;
            }
        }
        return null;
    }

    public ConferenceHall findConferenceHallById(int id) {
        for (ConferenceHall conferenceHall : conferenceHalls) {
            if (conferenceHall.getId() == id) {
                return conferenceHall;
            }
        }
        return null;
    }
}
