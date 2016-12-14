package com.yummyteam.fastcampus.forkit.model;

/**
 * Created by Dabin on 2016-12-13.
 */

public class Favors {
    private String id;
    private String user;
    private String restaurant;
    private String created_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", id = "+id+", restaurant = "+restaurant+", user = "+user+"]";
    }
}
