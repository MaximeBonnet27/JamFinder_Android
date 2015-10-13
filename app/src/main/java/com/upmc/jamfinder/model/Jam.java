package com.upmc.jamfinder.model;

import android.location.Location;

import java.util.Date;
import java.util.List;

/**
 * Created by maxime on 13/10/15.
 */
public class Jam {

    private String name;

    private User host;
    private List<User> participants;
    private Location location;
    private Date start;
    private Date end;



}
