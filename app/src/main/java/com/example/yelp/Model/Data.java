package com.example.yelp.Model;

import java.util.UUID;

public class Data {

    private String id;
    private String email;
    private String date;
    private String time;
    private String name;

    public Data(){

    }

    public Data(String name,String email, String date, String time) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
