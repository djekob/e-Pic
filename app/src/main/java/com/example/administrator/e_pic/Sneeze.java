package com.example.administrator.e_pic;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 9/02/2015.
 */
public class Sneeze implements Serializable {

    private String time;

    private User user;
    private int id;
    private double latitude;
    private double longitude;
    private int postal;

    public Sneeze(String time) {
        this.time = time;
    }

    public int getPostal() {
        return postal;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public Sneeze(String time, double longitude, double latitude, int postal) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.postal = postal;
    }

    public Sneeze(String time, double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.postal = 0;
    }

    public Sneeze(String time, User user) {
        this.time = time;
        this.user = user;
    }


    public Sneeze(String time, User user, int id) {
        this.time = time;
        this.user = user;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }

    @Override
    public String toString() {
        return "Sneeze{" +
                "time='" + time + '\'' +
                ", user=" + user +
                '}';
    }

}

