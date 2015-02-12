package com.example.administrator.e_pic;

/**
 * Created by Administrator on 9/02/2015.
 */
public class Sneeze {

    private String time;
    private User user;
    int id;

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

    @Override
    public String toString() {
        return "Sneeze{" +
                "time='" + time + '\'' +
                ", user=" + user +
                '}';
    }
}
