package com.MPK.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")

@Entity
@Table(name = "bus_lines")
public class Line {

    @Id
    @GeneratedValue
    private int id;
    private String number;
    private String type;

    @Transient
    private String refLink;

    @OneToMany
    private List<Stop> stops;

    public Line() {
    }

    public Line(String number, String type, String refLink) {
        this.number = number;
        this.type = type;
        this.refLink = refLink;
        this.stops = new ArrayList<>();
    }

    public List<Stop> getStopsDirectionFirst(){
        return stops.stream()
                .filter(stop -> stop.getDirection().equals(Stop.Direction.FIRST))
                .collect(Collectors.toList());
    }

    public List<Stop> getStopsDirectionSecond(){
        return stops.stream()
                .filter(stop -> stop.getDirection().equals(Stop.Direction.SECOND))
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getRefLink() {
        return refLink;
    }

    public void addStops(List<Stop> stops) {
        this.stops.addAll(stops);
    }

    public String getNumber() {
        return number;
    }

}

