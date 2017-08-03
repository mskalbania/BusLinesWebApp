package com.MPK.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stops")
public class Stop implements Comparable<Stop> {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Direction direction;
    @Transient
    private String refLink;
    @Transient
    private List<Departure> departures;

    public enum Direction {
        FIRST, SECOND
    }

    public Stop() {
    }

    public Stop(String name) {
        this.name = name;
        departures = new ArrayList<>();
        refLink = "";
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    @Override
    public int compareTo(Stop stop) {
        return this.name.compareTo(stop.name);
    }
}
