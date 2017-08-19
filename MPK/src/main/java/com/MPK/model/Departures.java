package com.MPK.model;

import java.util.ArrayList;
import java.util.List;

public class Departures {

    private List<String> departureHours;
    private DepartureType type;

    public enum DepartureType {
        WEEKDAYS, SATURDAYS, HOLIDAYS, FR_SA_SU, EVERYDAY
    }

    public Departures(DepartureType type) {
        this.departureHours = new ArrayList<>();
        this.type = type;
    }

    public Departures(List<String> departureHours, DepartureType type) {
        this.departureHours = departureHours;
        this.type = type;
    }

    public Departures() {
    }

    public void addHour(String hour) {
        departureHours.add(hour);
    }

    public List<String> getDepartureHours() {
        return departureHours;
    }

    public void setDepartureHours(List<String> departureHours) {
        this.departureHours = departureHours;
    }

    public DepartureType getType() {
        return type;
    }

    public void setType(DepartureType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + departureHours;
    }
}
