package org.example.coworking.models;

public class Workplace {
    private int id;
    private String name;
    private boolean isAvailable;

    public Workplace(int id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Workplace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
