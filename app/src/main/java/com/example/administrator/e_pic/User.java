package com.example.administrator.e_pic;

/**
 * Created by Administrator on 9/02/2015.
 */
public class User {

    private String username, firstname, name;
    private int age;

    public User(String username, String firstname, String name, int age) {
        this.username = username;
        this.firstname = firstname;
        this.name = name;
        this.age = age;
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