package com.yummyteam.fastcampus.forkit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dabin on 2016-12-06.
 */

public class RestaurantsData {
    @SerializedName("[0]")
    public Restaurants restaurantses;

    @SerializedName("0")
    public Restaurants getRestaurantses() {
        return restaurantses;
    }

    @SerializedName("0")
    public void setRestaurantses(Restaurants restaurantses) {
        this.restaurantses = restaurantses;
    }
}
