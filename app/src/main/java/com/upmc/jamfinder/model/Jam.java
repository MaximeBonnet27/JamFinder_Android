package com.upmc.jamfinder.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maxime on 13/10/15.
 */
public class Jam implements Serializable{

    private static int cpt = 0;

    private int id;
    private String name;

    private User host;
    private List<User> participants;
    private Date start;
    private Date end;
    private double latitute, longitude;

    private String details;

    public Jam(String name, User host, double latitute, double longitude, Date start, Date end) {
        this.name = name;
        this.host = host;
        this.latitute = latitute;
        this.longitude = longitude;
        this.start = start;
        this.end = end;
        this.id = cpt++;
        this.participants = new ArrayList<>();
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
        return new LatLng(latitute, longitude);
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getDetails() {
        return details;
    }
}
