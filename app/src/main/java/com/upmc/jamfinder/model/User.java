package com.upmc.jamfinder.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by maxime on 13/10/15.
 */
public class User {

    private static int cpt = 0;
    private int id;

    private String name;
    private String password;

    private ArrayList<User> friends;

    private ArrayList<Jam> createdJams;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        id = cpt++;
        createdJams = new ArrayList<>();
        friends = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriends() {
        User user1 = new User("Steve", "a");
        User user2 = new User("Stevie", "a");
        User user3 = new User("Steve-o", "a");
        User user4 = new User("Steven", "a");
        User user5 = new User("Catherine", "a");
        User user6 = new User("LOL", "a");
        ArrayList<User> liste = new ArrayList<>();
        liste.add(user1);
        liste.add(user2);
        liste.add(user3);
        liste.add(user4);
        liste.add(user5);
        liste.add(user6);
        return liste;
    }

    public ArrayList<Jam> getCreatedJams(){
        return createdJams;
    }

    //setName
    //setPassword
    //mail?
    //num tel?
    //activites preferées?
    //mes amis?
    //mes activiter crée?
    //mes activiter participé



}
