package com.yummyteam.fastcampus.forkit.model;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-12-15.
 */

public class RestaurantsList {
    private static RestaurantsList restaurantsList;
    private ArrayList<Results> list;

    private RestaurantsList(){

    }

    public static RestaurantsList getInstance(){
        if(restaurantsList == null){
            restaurantsList = new RestaurantsList();
        }
        return restaurantsList;
    }

    public void setList(ArrayList<Results> list){
        this.list = list;
    }
    public ArrayList<Results> getList(){
        return list;
    }
}
