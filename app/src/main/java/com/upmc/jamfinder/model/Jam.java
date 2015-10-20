package com.upmc.jamfinder.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by maxime on 13/10/15.
 */
public class Jam {

    private static int cpt = 0;

    private int id;
    private String name;

    private User host;
    private List<User> participants;
    private LatLng location;
    private Date start;
    private Date end;

    public Jam(String name, User host, LatLng location, Date start, Date end) {
        this.name = name;
        this.host = host;
        this.location = location;
        this.start = start;
        this.end = end;
        this.id = cpt++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getHost() {
        return host;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public LatLng getLocation() {
        return location;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
