import models.ConferenceHall;
import models.Workplace;

import java.util.ArrayList;

public class CoworkingSpace {
    private ArrayList<Workplace> workplaces;
    private ArrayList<ConferenceHall> conferenceHalls;

    public CoworkingSpace(ArrayList<Workplace> workplaces, ArrayList<ConferenceHall> conferenceHalls) {
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
        boolean success = false;
        for (int i = 0; i < conferenceHalls.size(); i++) {
            if (conferenceHalls.get(i).getId() == id) {
                conferenceHalls.get(i).setAvailable(isAvailable);
                conferenceHalls.get(i).setName(name);
                System.out.println("Обновление прошло успешно");
                success = true;
                break;
            }
        }
        if(!success) { System.out.println("Ошибка: не найдено помещение с таким id"); }
    }

    public void updateWorkplace(int id, String name, boolean isAvailable) {
        boolean success = false;
        for (int i = 0; i < workplaces.size(); i++) {
            if (workplaces.get(i).getId() == id) {
                workplaces.get(i).setAvailable(isAvailable);
                workplaces.get(i).setName(name);
                System.out.println("Обновление прошло успешно");
                success = true;
                break;
            }
        }
        if(!success) { System.out.println("Ошибка: не найдено помещение с таким id"); }
    }

    public void deleteConferenceHall(int id) {
        boolean success = false;
        for (int i = 0; i < conferenceHalls.size(); i++) {
            if (conferenceHalls.get(i).getId() == id) {
                conferenceHalls.remove(i);
                System.out.println("Удаление прошло успешно");
                success = true;
                break;
            }
        }
        if(!success) { System.out.println("Ошибка: не найдено помещение с таким id"); }
    }

    public void deleteWorkplace(int id) {
        boolean success = false;
        for (int i = 0; i < workplaces.size(); i++) {
            if (workplaces.get(i).getId() == id) {
                workplaces.remove(i);
                System.out.println("Удаление прошло успешно");
                success = true;
                break;
            }
        }
        if(!success) { System.out.println("Ошибка: не найдено помещение с таким id"); }
    }


}
