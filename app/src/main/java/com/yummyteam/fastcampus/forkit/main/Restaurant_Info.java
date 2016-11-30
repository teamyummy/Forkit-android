package com.yummyteam.fastcampus.forkit.main;

import java.util.ArrayList;

/**
 * Created by Dabin on 2016-11-29.
 */

public class Restaurant_Info {

    private int id;
    private String name;
    private ArrayList<String> imgs_url;
    private ArrayList<FoodMenu> menu;
    private String address;
    private String phone;
    private String Coordinate;
    private double score;
    private int favorite;
    private String review_content;
    private boolean isLike;
    private boolean isParking;
    private String operation_hour;
    private ArrayList<RestaurantTag> tags;
    private int total_review;
    private int total_like;

    public int getTotal_like() {
        return total_like;
    }

    public void setTotal_like(int total_like) {
        this.total_like = total_like;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getTotal_review() {
        return total_review;
    }

    public void setTotal_review(int total_review) {
        this.total_review = total_review;
    }

    public ArrayList<String> getImgs_url() {
        return imgs_url;
    }

    public void setImgs_url(ArrayList<String> imgs_url) {
        this.imgs_url = imgs_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodMenu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<FoodMenu> menu) {
        this.menu = menu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoordinate() {
        return Coordinate;
    }

    public void setCoordinate(String coordinate) {
        Coordinate = coordinate;
    }

    public double getScore() {
        return score;
    }


    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isParking() {
        return isParking;
    }

    public void setParking(boolean parking) {
        isParking = parking;
    }

    public String getOperation_hour() {
        return operation_hour;
    }

    public void setOperation_hour(String operation_hour) {
        this.operation_hour = operation_hour;
    }

    public ArrayList<RestaurantTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<RestaurantTag> tags) {
        this.tags = tags;
    }
}
