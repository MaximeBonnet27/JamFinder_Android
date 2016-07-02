package com.upmc.jamfinder.model;

import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private static int cpt = 0;

    private int id;
    private String name;
    private String password;
    private String mail;
    private ArrayList<User> friends;
    private ArrayList<Jam> createdJams;
    private ArrayList<Jam> goingToJams;

    public User(String name, String password,String mail) {
        this.name = name;
        this.password = password;
        this.mail=mail;
        id = cpt++;
        createdJams = new ArrayList<>();
        friends = new ArrayList<>();
        goingToJams = new ArrayList<>();
    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getFriends() {
        User user1 = new User("Steve", "a","Steve@gmail.com");
        User user2 = new User("Stevie", "a","Stevie@gmail.com");
        User user3 = new User("Steve-o", "a","Steve-o@gmail.com");
        User user4 = new User("Steven", "a","Steven@gmail.com");
        User user5 = new User("Catherine", "a","Catherine@gmail.com");
        ArrayList<User> liste = new ArrayList<>();
        liste.add(user1);
        liste.add(user2);
        liste.add(user3);
        liste.add(user4);
        liste.add(user5);
        return liste;
    }

    public ArrayList<Jam> getCreatedJams(){
        return createdJams;
    }

    public ArrayList<Jam> getGoingToJams() {
        return goingToJams;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o instanceof User){
            return ((User)o).getId()==id;
        }
        return false;
    }
}
