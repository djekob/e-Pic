package com.example.administrator.e_pic;

import java.io.Serializable;

/**
 * Created by Administrator on 9/02/2015.
 */
public class User implements Serializable {

    private String username, firstname, name;
    private int age, id, numberOfSneezes;

    public User(String username, String firstname, String name, int age, int id, int numberOfSneezes) {
        this.username = username;
        this.firstname = firstname;
        this.name = name;
        this.age = age;
        this.id = id;
        this.numberOfSneezes = numberOfSneezes;
    }

    public User(String username, String firstname, String secondname,int id) {
        this.username = username;
        this.firstname=firstname;
        this.name=secondname;
        this.id = id;
    }

    public int getNumberOfSneezes() {
        return numberOfSneezes;
    }

    public void setNumberOfSneezes(int numberOfSneezes) {
        this.numberOfSneezes = numberOfSneezes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
